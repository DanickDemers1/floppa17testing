# TP1
## 1. Planification Github

1. 1 pour le *Project* comprenant les colonnes et les issues associées
  ![image](https://user-images.githubusercontent.com/46549578/151637184-6b4dde97-ef80-4232-9d39-1f8c6ffbf25e.png)

2. 1 pour le milestone comprenant le titre, la description et les issues associées
  ![image](https://user-images.githubusercontent.com/46549578/151637388-4bea6c8e-401d-4f1d-b0fb-a910f1acef4c.png)

3. 3 pour les issues avec tous les éléments demandés visibles
  ![image](https://user-images.githubusercontent.com/46549578/151637497-4eca3636-cd82-443d-bd1b-0176fdb4ef6b.png)
  ![image](https://user-images.githubusercontent.com/46549578/151637541-0469db5e-8b6a-4c18-bc3e-3b7fca84d7b6.png)
  ![image](https://user-images.githubusercontent.com/46549578/151637578-7158c87b-9d5a-4418-9652-278ae4387a72.png)

4. 3 pour les PR avec tous les éléments demandés visibles
  ![image](https://user-images.githubusercontent.com/46549578/151637690-8c85bda8-13d8-4b74-8d37-8597ccba0488.png)
  ![image](https://user-images.githubusercontent.com/46549578/151637725-eeaa4dc2-1120-4ee8-9fa1-e0fc6d373f40.png)
  ![image](https://user-images.githubusercontent.com/46549578/151637745-5c2c576c-9dd3-4331-b8ee-72a8791f8cda.png)

* * *
## 2. Convention commits
* _Comment nommer les commits selon leur type/section/grosseur/etc.?_
  
 On s'inspire de la [convention suggérée](https://www.conventionalcommits.org/en/v1.0.0/) dans l'énoncé pour le nommage dans le sens où nous utiliserons les types tels que décrits (_feat: fix: ..._) avec un footer facultatif au besoin. Nous utiliserons des phrases simples pour décrire le commit, se rapprochant le plus au titre de l'issue.
* _Quoi et quand commiter?_
  
 Pour éviter d'avoir trop de commits et ainsi faciliter la lecture; un commit correspond à une issue dans l'onglet Project de Github. Aussi, un commit ne doit pas être fait si l'application ne compile pas ou qu'il cause un bug.
* _Quelles sont les branches de base (qui sont communes et qui existeront toujours) et quels sont leurs rôles (chacune)?_
  
 En suivant les recommandations [GitFlow](https://www.toptal.com/software/trunk-based-development-git-flow) fournies, nous utiliserons les branches de base suivantes: 
  * La branche "**develop**": la branche principale de développement avec un accès strict.
  * La branche "**feature**": la branche qui contient les features implémentées chacune dans une branche fille. Une fois la fonctionnalité validée, elle est merged à **develop**.
  * La branche "**master**": à la fin d'une itération et lorsque le développement est finalisé dans la branche **develop** cette dernière est merged dans _master_ pour la remise.
* _Quelle branche est LA branche principale (contenant le code officiellement intégré et pouvant être remis)?_
  
 Comme spécifié dans le point précédent, c'est la branche **master** qui sera la branche principale sur laquelle le code sera merged au moment de la remise.
* _Quand créer une nouvelle branche?_
  
 Chaque feature à réaliser, donc pour chaque issue dans l'onglet Project de Github une branche est créée sous la branche **feature**, par exemple: la création de vendeur est une issue, on créé une branche sous **feature** qui donne _feature/seller_. Pour le contenu hors code (les fichiers .md par exemple), un branche créée depuis la branche **develop** avec un nom significatif au contenu.
* _Quand faire une demande de changement / d'intégration (pull request / merge request) et sur quelle branche la faire?_
  
 Une fois une feature terminée & testée et ne cause pas de conflits, une demande d'intégration est faite sur la branche **develop**, après review des membres qui la valideront.
* * *
## `.gitignore`
Pour générer le fichier .gitignore, nous avons utilisé le site [toptal](https://www.toptal.com/developers/gitignore) en spécifiant les outils: java, intelliJ et Maven, une référence est incluse dans le fichier.