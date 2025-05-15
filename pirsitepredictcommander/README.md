## PIRSitePredictCommander

PIRSitePredict from command line

Requirement:
- Java 8. Please follow this link to download and install.
- Latest HMMER software. Please follow this link to download and install.

Run the supplied shell script. If you run this script with no arguments, you will be presented with the usage instructions:

$ ./pirsitepredict.sh
You will see the following usage instructions:



```bash
Welcome to PIRSitePredict
usage: java -Xms512M -Xmx2048M -jar PIRSitePredictorCommander.jar [options]
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