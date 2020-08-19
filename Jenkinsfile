pipeline {

  agent any
  
      environment {
		URL_NEXUS_HOSTED_REPO_SNAPSHOTS = "http://nexus.pic.soda.dgfip/repository/maven_soda_snapshots/"
		URL_NEXUS_HOSTED_REPO_RELEASES = "http://nexus.pic.soda.dgfip/repository/maven_soda_releases/"    
    	VERSION_JDK = "open-jdk11"
    	VERSION_MAVEN ="Maven 3.6"
    	REP_URL = "https://forge.dgfip.finances.rie.gouv.fr/dgfip/soda/testspic/TestPicIhm.git"
    	GITLAB_CREDENTIAL_ID = "gitlab_mutualise"
    	GITLAB_ID = "gitlabmutualise"
    	NEXUS_CREDENTIAL_ID = "jenkins_nexus"
    	SONAR_CREDENTIAL_ID ="sonar_token"
    	GIT_USER_EMAIL = "wenceslas.petit@gmail.com"
    	isRelease = false
    	skipTests = false
    	skipSonar = false
    	debug = false
        }

    tools {

        maven "${VERSION_MAVEN}"
        jdk "${VERSION_JDK}"
    }
    
     options {
   		 disableConcurrentBuilds()
    	 buildDiscarder(logRotator(numToKeepStr: '30'))
    	 timeout(time: 25, unit: 'MINUTES')
   		}

    parameters {       
        booleanParam(name: 'isRelease', defaultValue: false, description: 'Produire une release à partir de ce build')
 		booleanParam(name: 'skipSonar', defaultValue: false, description: 'Eviter l\'analyse sonarqube')
 		booleanParam(name: 'skipTests', defaultValue: false, description: 'Ne pas exécuter les tests unitaires')
 		booleanParam(name: 'debug', defaultValue: false, description: 'Activer les options de debug maven')
    }   


   stages {

	stage('Test état de la PIC ') 	{
		steps {
			script {
			/*
				def responseSelenium = httpRequest "http://selenium.pic.soda.dgfip/grid/console"
				println('Status: ' + responseSelenium.status)
				println('Content: ' + responseSelenium.content)
				
				def responseZAP = httpRequest "http://zap.pic.soda.dgfip"
				println('Status: ' + responseZAP.status)
				println('Content: ' + responseZAP.content)
			*/	
				def responseCoca = httpRequest "http://coca.appli.dgfip"
				println('Status: ' + responseCoca.status)
				println('Content: ' + responseCoca.content)
			}
		
		}
	
	}


	 stage('Configuration du build') {
       // agent none
        steps {
           
            withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", passwordVariable: 'NEXUS_PWD', usernameVariable: 'NEXUS_LOGIN')])
    			{
    			
    			script {
			
			    env.MAVEN_OPTS_ALT ="-Xms2G -Xmx2G\
			    					-Drepo-snapshots.id=nexus\
			    					-Drepo-snapshots.login=${NEXUS_LOGIN}\
			    					-Drepo-snapshots.pwd=${NEXUS_PWD}\
			    					-Drepo-releases.id=nexus-releases\
			    					-Drepo-releases.login=${NEXUS_LOGIN}\
			    					-Drepo-releases.pwd=${NEXUS_PWD}\
			    					-DaltSnapshotDeploymentRepository=nexus::${URL_NEXUS_HOSTED_REPO_SNAPSHOTS}\
			    					-DaltReleaseDeploymentRepository=nexus-releases::${URL_NEXUS_HOSTED_REPO_RELEASES}"
   				 		

   				 
   				 		}
   				 }
   				 
   				 
   			  withCredentials([usernamePassword(credentialsId: "${GITLAB_CREDENTIAL_ID}", passwordVariable: 'PASSWORD_VAR', usernameVariable: 'USERNAME_VAR')])
    				{
    				script {
    						env.MAVEN_OPTS_ALT = env.MAVEN_OPTS_ALT + 
    								" \
    								-Dgitlab.id=${GITLAB_ID}\
    								-Dgitlab.login=${USERNAME_VAR}\
    								-Dgitlab.pwd=${PASSWORD_VAR}"
    						}				
    				}	 
           
           
            script {
            		print "configuration du build"
            		
            		print "env.MAVEN_OPTS_ALT = ${env.MAVEN_OPTS_ALT}"
            		
            		//def GIT_CREDENTIAL_ID = scm.getUserRemoteConfig()[0].getCredentialsId()
            		//print "credential_Id =" +  ${GITLAB_CREDENTIAL_ID}
            		
            		//def GIT_URL = scm.getUserRemoteConfig()[0].getUrl()
            		//print "GIT_URL =" + ${GIT_URL}
            		
            		//def GIT_BRANCH = scm.getBranches()[0]
            		//print "branches =" +  ${GIT_BRANCH}
            		
            		
            		env.PARAMETRES_TEST=""
   					if (params.skipTests == true)
   						{
    					env.PARAMETRES_TEST="-DskipTests"
						}
					env.PARAMETRES_DEBUG=""
   					if (params.debug == true)
   						{
    					env.PARAMETRES_DEBUG="-e -X"
						}
						
					print "env.PARAMETRES_DEBUG = ${env.PARAMETRES_DEBUG}"
				
            		}
        		}
    		}
	

    stage('Configuration Git')
    {

    		steps{
    		    	withCredentials([usernamePassword(credentialsId: "${GITLAB_CREDENTIAL_ID}", passwordVariable: 'PASSWORD_VAR', usernameVariable: 'USERNAME_VAR')])
    				{
	        		sh 'git config --global http.sslVerify false'
	        		sh 'git config user.name "${USERNAME_VAR}"'
	        		sh 'git config user.email "${GIT_USER_EMAIL}"'
    				}
    		}
	}

	stage('checkout du code') {
	
	
	steps {
		
	
		checkout(
			[	$class: 'GitSCM',
				branches: [[name: '*/master']],
				browser: [$class: 'GitLab', repoUrl: "${REP_URL}"],
				doGenerateSubmoduleConfigurations: false,
			    extensions: scm.extensions + [[$class: 'CleanCheckout'], [$class: 'LocalBranch', localBranch: '**']],
				submoduleCfg: [],
				userRemoteConfigs: [
					[credentialsId: "${GITLAB_CREDENTIAL_ID}", url: "${REP_URL}"]
				]
			])
		}
	}
	


    stage('Build') {
    when {
        expression { params.isRelease != true }
    } 
      
	//source : https://stackoverflow.com/questions/57420331/pass-credentials-to-maven-from-jenkins
 	//source pour surcharge des repositories :
 	//https://stackoverflow.com/questions/3298135/how-to-specify-mavens-distributionmanagement-organisation-wide
    //-DaltSnapshotDeploymentRepository=snapshots::default::https://YOUR_NEXUS_URL/snapshots
	//-DaltReleaseDeploymentRepository=releases::default::https://YOUR_NEXUS_URL/releases

    
      steps {
		script {
	 			def PARAMETRES_DEBUG = "${env.PARAMETRES_DEBUG}"
	 			def PARAMETRES_TEST = "${env.PARAMETRES_TEST}"
				}
				
      		 withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", passwordVariable: 'NEXUS_PWD', usernameVariable: 'NEXUS_LOGIN')])
    			{
    			   			
    			withMaven(
    				//globalMavenSettingsConfig: 'maven-global',
    				globalMavenSettingsConfig: 'maven-global-parametrable',
    				jdk: "${VERSION_JDK}",
    				maven: "${VERSION_MAVEN}",
    				//mavenOpts: '-Xms2G -Xmx2G -Drepo-snapshots.id=nexus -Drepo-snapshots.login=${NEXUS_LOGIN} -Drepo-snapshots.pwd=${NEXUS_PWD}',
    				mavenOpts: "${env.MAVEN_OPTS_ALT}",
    				//mavenLocalRepo: '.repository'
    				mavenLocalRepo: '~/.m2/.repository'
    				)
    				{
    				//sh 'mvn help:effective-settings'
					sh 'mvn ${PARAMETRES_DEBUG} -DskipTests   clean deploy '
					}
				}
      }
    }


    stage('Analyse Sonarqube') {

      when {
        expression { params.skipSonar != true }
      }
		
      steps {
      
      		script {
	 			def PARAMETRES_DEBUG = "${env.PARAMETRES_DEBUG}"
	 			def PARAMETRES_TEST = "${env.PARAMETRES_TEST}"
				}
      
        withMaven(
          jdk: "${VERSION_JDK}", 
          maven: "${VERSION_MAVEN}",
          options: [ pipelineGraphPublisher(disabled: true) ]
        ) {
          withSonarQubeEnv(credentialsId: "${SONAR_CREDENTIAL_ID}",installationName :'Sonarqube-server') {
            sh 'mvn ${PARAMETRES_DEBUG} verify sonar:sonar ${PARAMETRES_TEST} -Dintegration-tests.skip=true -Dmaven.test.failure.ignore=true'
          }
        }
      }
    }
    

    


    stage('Release prepare') {

      when {
        expression { params.isRelease == true }
      }

      steps {
      
      		script {
	 			def PARAMETRES_DEBUG = "${env.PARAMETRES_DEBUG}"
	 			def PARAMETRES_TEST = "${env.PARAMETRES_TEST}"
				}
      
      		withCredentials([usernamePassword(credentialsId: "${GITLAB_CREDENTIAL_ID}", passwordVariable: 'PASSWORD_VAR', usernameVariable: 'USERNAME_VAR')])
    			{
 			 	withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", passwordVariable: 'NEXUS_PWD', usernameVariable: 'NEXUS_LOGIN')])
	    		{
	    			withMaven(
	    				globalMavenSettingsConfig: 'maven-global-parametrable',
	    				jdk: "${VERSION_JDK}",
	    				maven: "${VERSION_MAVEN}",
	    				//mavenOpts: '-Xms2G -Xmx2G -Dusername=${USERNAME_VAR} -Dpassword=${PASSWORD_VAR} -Drepo-releases.id=nexus -Drepo-releases.login=${NEXUS_LOGIN} -Drepo-releases.pwd=${NEXUS_PWD}',
	    				mavenOpts: "${env.MAVEN_OPTS_ALT} -Dusername=${USERNAME_VAR} -Dpassword=${PASSWORD_VAR}",
	    				mavenLocalRepo: '.repository',
	    				options: [ pipelineGraphPublisher(disabled: true) ])
	    				{
	 					sh 'mvn ${PARAMETRES_DEBUG} -DskipTests -B -Dresume=false  release:clean release:prepare ' 
						}
					}
    			}
        	}
       }
        
      stage('Release perform') {

	      when {
	        expression { params.isRelease == true }
	      }
	
	      steps {
	      
	      		script {
		 			def PARAMETRES_DEBUG = "${env.PARAMETRES_DEBUG}"
		 			def PARAMETRES_TEST = "${env.PARAMETRES_TEST}"
					}
	      
	      		withCredentials(
	      		[usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", passwordVariable: 'NEXUS_PWD', usernameVariable: 'NEXUS_LOGIN')])
	    			{
	 		
	    			withMaven(
	    				globalMavenSettingsConfig: 'maven-global-parametrable',
	    				jdk: "${VERSION_JDK}",
	    				maven: "${VERSION_MAVEN}",
	    				//mavenOpts: '-Xms2G -Xmx2G -Drepo-releases.id=nexus -Drepo-releases.login=${NEXUS_LOGIN} -Drepo-releases.pwd=${NEXUS_PWD} ',
	    				mavenOpts: "${env.MAVEN_OPTS_ALT}",
	    				mavenLocalRepo: '.repository',
	    				options: [ pipelineGraphPublisher(disabled: true) ])
	    				{
	 					sh 'mvn ${PARAMETRES_DEBUG} -DskipTests -B -Dresume=false   release:perform ' 
						}
	
	    			}
	        	}
      }


  }

/*
  post {

    changed {
      emailext(
        to: 'bureau.si1a-soda@dgfip.finances.gouv.fr, COURRIELS_RESPONSABLES_PROJET',
        recipientProviders: [ [ $class: 'DevelopersRecipientProvider' ] ],
        subject: '${DEFAULT_SUBJECT}',
        body: '${DEFAULT_CONTENT}',
      )
    }
    always {
      step([ $class: 'ClaimPublisher' ]);
    }
  }
*/
}    	