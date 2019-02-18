# Version history
* 0.1 - Prints out sample lines in database. 

* 0.2 - Possible to peform basic search in database (future release). 

# The day tour search engine
Search engine for day tours offered in Iceland. Availability-based booking with hotel pickup option. Created in the course Software Development (HBV401G). 
# Branching model for Git
The plan is to use a simplified version of GitFlow as a branching model for git. 
[About GitFlow](https://datasift.github.io/gitflow/IntroducingGitFlow.html)

As well as using this model we use: 
* git revert instead of git reset. 
* git merge instead of git rebase.

* We have a master branch, dev (development) branch
and feature / hotfix branches off of the dev branch
* The master branch only has release ready versions of the app. 
The idea is the commits track the app between sprints. So one
commit for sprint 1 and then the next commit for sprint 2. 
## The general workflow for git
* Create a feature / hotfix branch off of the dev branch. 
Develop feature / fix bug. 
* When fininshed, pull from master into branch and resolve conflicts. 
* Git push your branch to the remote branch of the same name. 
* Create a pull request and assign reviewers. 
* Merge with the dev branch. 
* When all features for a particular sprint have been implemented,
merge with the master branch. 

These concepts and more useful git commands are covered in a fun game on git branching
(which could also be used for illustrative purposes): 
https://learngitbranching.js.org/

We use Slack to communicate: 
https://daytoursearch.slack.com

# Project plan
1a. User stories (a) (27. Jan)

1b. Product backlog (b) (10. Feb)

2. Domain model (24. Feb)

3. Design model (10. Mar)

4. Test cases (31. Mar)

5. Final product (14. Apr)
