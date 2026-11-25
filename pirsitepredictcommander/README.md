## PIRSitePredictCommander

Command-line version of PIRSitePredict. It predicts functional sites (such as catalytic and binding sites) in protein sequences using PIR Site Rules (PIRSR): it reads InterProScan 5 XML results for the query proteins, aligns them to the rules' site HMM models with HMMER 3, and reports the predicted sites in TSV, GFF3 or XML format.

### Requirements
- Java 8 or later ([Eclipse Temurin](https://adoptium.net/) or any other JDK)
- [HMMER 3](http://hmmer.org/) (`hmmsearch` and `hmmalign`)
- [Apache Maven](https://maven.apache.org/) to build
- InterProScan 5 XML output for the proteins to analyze
- The PIR Site Rule data directory (site rules, site HMM models and rule template sequences), passed with `-d`. This data is not currently distributed with the code.

### Build
```bash
mvn package
mvn dependency:copy-dependencies
```

Set the paths to `hmmsearch` and `hmmalign` in `src/main/resources/PIRSiteScan.properties` before building, or pass them with `-S` and `-A` at run time.

### Run
With no arguments, the tool prints its usage:

```bash
java -Xms512M -Xmx2048M -cp "target/classes:target/dependency/*" \
  org.proteininformationresource.pirsr.PIRSitePredictCommander
```

Example:

```bash
java -Xms512M -Xmx2048M -cp "target/classes:target/dependency/*" \
  org.proteininformationresource.pirsr.PIRSitePredictCommander \
  -i proteins.iprscan.xml -d pirsr_data/ -o results/ -f TSV,GFF3 -O Bacteria
```

### Options

```bash
Welcome to PIRSitePredict
usage: java -Xms512M -Xmx2048M -cp "target/classes:target/dependency/*" org.proteininformationresource.pirsr.PIRSitePredictCommander [options]
Available options:

          -A,--hmmalign <PATH/TO/HMMALIGN/COMMAND>              Path to the HMMER 3 hmmalign command.
          -d,--pirsr-data-dir <PATH/TO/PIRSR/Data/DIR>          Required, the directory where the PIR Site Rule data
                                                                (Site Rule, Site HMM models and Rule template sequence)
                                                                files are located.
          -e,--eval <E-VALUE>                                   The e-value cutoff of matches to SRHMM models (default:
                                                                0.0001).
          -F,--force-overwrite                                  Force overwrite output directory.
          -f,--formats <PREDICTION-OUTPUT-FORMATS>              Comma separated list of output formats. Supported
                                                                formats are TSV, GFF3, XML.
          -h,--help                                             Show help.
          -i,--iprscan-xml <PATH/TO/IPRSCAN/XML/FILE>           Required, the path to InterProScan XML file.
          -l,--log-file <PATH/TO/LOG/FILE>                      Path to log file.
          -o,--output-dir <PATH/TO/OUTPUT/DIR>                  Output directory. It will be created automatically if it
                                                                does not exist. Default: 'outputDir' in the current
                                                                directory.
          -O,--organism <TAXONOMY>                              The taxonomic classification is composed of the kingdom,
                                                                optionally followed by the name of a sub-taxon, to
                                                                further limit the application of the UniRule to any
                                                                taxonomic level. Valid values for kingdom are:
                                                                Eukaryota,Bacteria, Archaea, Viruses, Bacteriophage, Plastid and Mitochondrion. Default:
                                                                Eukaryota.
          -S,--hmmsearch <PATH/TO/HMMSEARCH/COMMAND>            Path to the HMMER 3 hmmsearch command.
			
```