node {
  try{
    stage 'checkout project'
    checkout scm

    stage 'check env'
    sh "mvn -v"
    sh "java -version"

    stage 'package'
    sh "mvn clean validate compile package"

    stage 'Docker build'
    sh 'docker build -t backend --tag=openjdk:8 --build-arg ENVIRONMENT=dev --rm=true .'

    stage 'Docker run'
    sh "docker run -p 81:8050 backend"
  } catch(e) {
    throw e;
  }
}