# PIR UniRule: parser, writer, and propagation tool for PIR rules

## What is PIRSR and PIRNR?
PIRSR (site rule) and PIRNR (name rule) are annotation rules based on PIRSF (protein families with full-length sequence similarity). These rules are manually curated to accurately and automatically annotate protein sequences. PIRSRs annotate sequence features for sites, such as catalytic site, and binding site. PIRNRs annotations may include protein name, keywords, and general annotation, such as function, subcellular location, and pathway.

This project is the source code of the PIR Site Rule and PIR Name Rule parser and writer. It reads rules in the UniRule flat-file format, checks their syntax and templates, prints them as UniRule or HTML, and propagates rule annotations to UniProtKB entries.

## Requirements
- Java 8 or later (tested with Java 21)
- The jars in `lib/` (commons-cli, commons-io)
- [commons-lang3](https://central.sonatype.com/artifact/org.apache.commons/commons-lang3) (any 3.x version). Download the jar and place it in `lib/`.

## Build
From this directory (`pir_unirule/`):

```bash
mkdir -p build
find src -name '*.java' -not -path '*/test/*' > sources.txt
javac -encoding UTF-8 --release 8 -cp "lib/*" -d build @sources.txt
```

On Windows, use `;` instead of `:` in the classpath in the commands below.

## Run
Show all options:

```bash
java -cp "build:lib/*" org.proteininformationresource.pirrule.PIRUniRule -h
```

Every run needs an action (`-a`). The actions and their required options are:

| Action | What it does | Required options |
|---|---|---|
| `checkRuleSyntax` | Parses a rule file and reports syntax errors | `-f` rule file |
| `printRule` | Prints rules in UniRule or HTML format | `-f` rule file; optional `-r` rule ID, `-l` ID list, `-F UniRule\|HTML` |
| `checkRuleTemplate` | Checks each rule's template entries against Swiss-Prot data | `-f` rule file, `-s` Swiss-Prot `.dat` file |
| `checkHMM3Model` | Checks a rule against HMMER3 template-to-model search output | `-f` rule file, `-r` rule ID, `-t` search output file |
| `propagate` | Applies rules to UniProtKB entries and reports the results | `-p` propagation directory |

Output goes to standard output unless `-o <file>` is given. `-D` prints the rule line by line while parsing (debug mode).

### Examples
The [`examples/`](./examples/) folder contains one PIR Site Rule, `PIRSR000005-1.uru`, which annotates the covalent heme c binding sites of a PIRSF family. Each rule in a file starts with an `AC` (accession) line and ends with `//`.

```bash
# Check the syntax of the example rule (no output means no errors; add -D to print it line by line)
java -cp "build:lib/*" org.proteininformationresource.pirrule.PIRUniRule \
  -a checkRuleSyntax -f examples/PIRSR000005-1.uru

# Print the rule in UniRule format
java -cp "build:lib/*" org.proteininformationresource.pirrule.PIRUniRule \
  -a printRule -f examples/PIRSR000005-1.uru

# Print the rule as HTML
java -cp "build:lib/*" org.proteininformationresource.pirrule.PIRUniRule \
  -a printRule -f examples/PIRSR000005-1.uru -r PIRSR000005-1 -F HTML -o PIRSR000005-1.html

# Check rule templates against Swiss-Prot entries (needs a UniProtKB flat file)
java -cp "build:lib/*" org.proteininformationresource.pirrule.PIRUniRule \
  -a checkRuleTemplate -f examples/PIRSR000005-1.uru -s uniprot_sprot.dat

# Propagate rules (see below for the directory contents)
java -cp "build:lib/*" org.proteininformationresource.pirrule.PIRUniRule \
  -a propagate -p propagation_dir/
```

### Propagation directory
For `propagate`, the directory given with `-p` must contain:

- one rule file in UniRule flat-file format (`.uru`)
- one UniProtKB data file in flat-file format (`.dat`)
- one alignment file (`.gff`)

For each rule, the tool writes result files into the same directory, named by rule accession, for example `<RuleAC>_SP_TP.txt`, `<RuleAC>_SP_FP.txt` and `<RuleAC>_SP_FN.txt` for Swiss-Prot true positives, false positives and false negatives, and the matching `_TR_` files for TrEMBL.

## Documentation
API documentation (Javadoc) is in [`doc/`](./doc/index.html).
