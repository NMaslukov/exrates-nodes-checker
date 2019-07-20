node {
  try{
    stage 'checkout project'
    checkout scm

    stage 'check env'
    sh "mvn -v"
    sh "java -version"

    stage 'test'
    sh "mvn test"

    stage 'package'
    sh "mvn package"

    stage 'Artifact'
    step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])

    stage 'Deploy'
    sh "java -jar
    /target/exrates-nodes-checker-0.0.1-SNAPSHOT.jar"
  }catch(e){
    throw e;
  }
}