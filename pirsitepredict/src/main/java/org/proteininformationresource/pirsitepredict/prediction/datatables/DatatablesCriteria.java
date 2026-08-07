package org.proteininformationresource.pirsitepredict.prediction.datatables;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Minimal replacement for Dandelion's {@code DatatablesCriterias}, parsed from
 * the standard DataTables 1.10+ server-side request parameters. Only exposes
 * the surface actually used by {@code PredictionTSVReportFileRepository}.
 */
public class DatatablesCriteria {

	public enum Direction {
		ASC, DESC
	}

	public static class Column {

		private final String name;
		private final boolean filterable;
		private final String search;
		private final Direction sortDirection;

		public Column(String name, boolean filterable, String search, Direction sortDirection) {
			this.name = name;
			this.filterable = filterable;
			this.search = search;
			this.sortDirection = sortDirection;
		}

		public String getName() {
			return name;
		}

		public boolean isFilterable() {
			return filterable;
		}

		public String getSearch() {
			return search;
		}

		public Direction getSortDirection() {
			return sortDirection;
		}
	}

	private final long draw;
	private final int start;
	private final int length;
	private final String search;
	private final List<Column> columns;

	public DatatablesCriteria(long draw, int start, int length, String search, List<Column> columns) {
		this.draw = draw;
		this.start = start;
		this.length = length;
		this.search = search;
		this.columns = columns;
	}

	public static DatatablesCriteria from(HttpServletRequest request) {
		String drawParam = request.getParameter("draw");
		long draw = drawParam == null ? 0L : Long.parseLong(drawParam);
		int start = intParam(request, "start");
		int length = intParam(request, "length");

		String search = request.getParameter("search[value]");

		List<Column> columns = new ArrayList<Column>();
		for (int i = 0; ; i++) {
			String name = request.getParameter("columns[" + i + "][name]");
			if (name == null) {
				break;
			}
			String searchable = request.getParameter("columns[" + i + "][searchable]");
			String columnSearch = request.getParameter("columns[" + i + "][search][value]");
			Direction sortDirection = null;
			for (int o = 0; ; o++) {
				String orderColumn = request.getParameter("order[" + o + "][column]");
				if (orderColumn == null) {
					break;
				}
				if (Integer.parseInt(orderColumn) == i) {
					String dir = request.getParameter("order[" + o + "][dir]");
					sortDirection = "desc".equalsIgnoreCase(dir) ? Direction.DESC : Direction.ASC;
				}
			}
			columns.add(new Column(name, !"false".equals(searchable), columnSearch, sortDirection));
		}
		return new DatatablesCriteria(draw, start, length, search, columns);
	}

	private static int intParam(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		return value == null ? 0 : Integer.parseInt(value);
	}

	public long getDraw() {
		return draw;
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}

	public String getSearch() {
		return search;
	}

	public List<Column> getColumnDefs() {
		return columns;
	}

	public boolean hasOneFilterableColumn() {
		for (Column column : columns) {
			if (column.isFilterable()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasOneFilteredColumn() {
		for (Column column : columns) {
			if (isNotBlank(column.getSearch())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasOneSortedColumn() {
		for (Column column : columns) {
			if (column.getSortDirection() != null) {
				return true;
			}
		}
		return false;
	}

	public List<Column> getSortingColumnDefs() {
		List<Column> sorted = new ArrayList<Column>();
		for (Column column : columns) {
			if (column.getSortDirection() != null) {
				sorted.add(column);
			}
		}
		return sorted;
	}

	private static boolean isNotBlank(String value) {
		return value != null && value.trim().length() > 0;
	}
}
