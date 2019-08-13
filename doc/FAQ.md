# kpointers: FAQ

- Q: When I launch tests from Eclipse, it reports "No tests found with test runner 'JUnit5':

  A: We haven't been able to fix this. The workaround is to:
  
  1. Launch the tests from Gradle for debugging, as in ``gradlew test --debug-jvm``
  2. Set breakpoint in Eclipse (or the chosen tool)
  3. Launch remote debugging from Eclipse, using port 5005. The debugger will stop in the first breakpoint it finds
