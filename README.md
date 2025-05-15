## PIRUniRule

This repository is for PIRUniRule (PIR SiteRule and PIR NameRule) and related projects.

The rapid growth of protein records in UniProtKB, driven by genome sequencing, poses a major challenge for functional annotation. To address this, UniProt developed UniRule, a scalable, rule-based annotation system that integrates expert-curated methods (RuleBase, HAMAP, PIRSR, PIRNR) and leverages InterPro protein family signatures, taxonomic data, and experimental evidence. UniRule automatically propagates annotations from reviewed to unreviewed proteins meeting shared criteria, significantly expanding coverage, enabling efficient large-scale functional predictions for proteins unlikely to be experimentally characterized.

- [pir_unirule](./pir_unirule/): 
This project is the source code of PIR Site Rule and PIR Name Rule parser and writer.

- [pirsitepredict](./pirsitepredict/):
This project is the source code of PIRSitePredict website at https://research.bioinformatics.udel.edu/PIRSitePredict/.

- [pirsitepredictcommander](./pirsitepredictcommander/):
This project is the source code of PIRSitePredict command line tool as described in[here](https://research.bioinformatics.udel.edu/PIRSitePredict/documentation/standalone).

## Publications
Vasudevan S, Vinayaka CR, Natale DA, Huang H, Kahsay RY, Wu CH. [Structure-guided rule-based annotation of protein functional sites in UniProt knowledgebase](https://link.springer.com/protocol/10.1007/978-1-60761-977-2_7). Methods Mol Biol. 2011;694:91-105. doi: 10.1007/978-1-60761-977-2_7. PMID: 21082430.

Chen C, Wang Q, Huang H, Vinayaka CR, Garavelli JS, Arighi CN, Natale DA, Wu CH. [PIRSitePredict for protein functional site prediction using position-specific rules](https://academic.oup.com/database/article/doi/10.1093/database/baz026/5363830). Database (Oxford). 2019, baz026. [PMID: 30805646].

MacDougall A, Volynkin V, Saidi R, Poggioli D, Zellner H, Hatton-Ellis E, Joshi V, O'Donovan C, Orchard S, Auchincloss AH, Baratin D, Bolleman J, Coudert E, de Castro E, Hulo C, Masson P, Pedruzzi I, Rivoire C, Arighi C, Wang Q, Chen C, Huang H, Garavelli J, Vinayaka CR, Yeh LS, Natale DA, Laiho K, Martin MJ, Renaux A, Pichler K; UniProt Consortium. [UniRule: a unified rule resource for automatic annotation in the UniProt Knowledgebase](https://academic.oup.com/bioinformatics/article/36/17/4643/5836494). Bioinformatics. 2020 Nov 1;36(17):4643-4648. [PMID: 32399560].

