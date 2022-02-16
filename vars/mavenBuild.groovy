def call(body) {
  def args = [
brunch : '' , 
url : '', 
service  : ''

  ]
  body.resolveStrategy= Closure.DELEGATE_FIRST
  body.deligate = args 
  body()
  echo "INFO:${args.service}"
  echo "INFO:${args.branch}"

  pipeline {
    agent any 
    tools {
      maven 'maven'
      jdk 'jdk'
    }
  stages 
  {
    stage ('Git Checkout')
    {
      steps {
        Checkout([
          $class: 'GitSCM', 
          branchs: [[name: args.branch]],
          userRemoteConfigs: [[url: args.url]]
        ])
      } 
    }
    stage('Build') {
      steps {
        bat 'mnv -Dmaven.test.failure.ignore=true'
      }
    }
  }
  }

}
