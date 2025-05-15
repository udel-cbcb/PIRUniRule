## PIRUniRule

This repository is for PIRUniRule (PIR SiteRule and PIR NameRule) and related projects.

The rapid growth of protein records in UniProtKB, driven by genome sequencing, poses a major challenge for functional annotation. To address this, UniProt developed UniRule, a scalable, rule-based annotation system that integrates expert-curated methods (RuleBase, HAMAP, PIRSR, PIRNR) and leverages InterPro protein family signatures, taxonomic data, and experimental evidence. UniRule automatically propagates annotations from reviewed to unreviewed proteins meeting shared criteria, significantly expanding coverage, enabling efficient large-scale functional predictions for proteins unlikely to be experimentally characterized.

- [pir_unirule](./pir_unirule/): 
This project is the source code of PIR Site Rule and PIR Name Rule parser and writer.

- [pirsitepredict](./pirsitepredict/):
This project is the source code of PIRSitePredict website.

- [pirsitepredictcommander](./pirsitepredictcommander/):
This project is the source code of PIRSitePredict command line tool as described in[here](https://research.bioinformatics.udel.edu/PIRSitePredict/documentation/standalone).
