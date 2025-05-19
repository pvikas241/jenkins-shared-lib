def call() {
    stage('Hello World') {
        script {
            echo "Hello World from Shared Library!"
            error("Intentional failure for testing")
        }
    }
}
