def call() {
    stage('Hello World') {
        steps {
            script {
                echo "Hello World from Shared Library!"
            }
        }
    }
}
