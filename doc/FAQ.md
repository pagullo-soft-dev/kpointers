# kpointers: FAQ

- Q: When I launch tests from Eclipse, it reports "No tests found with test runner 'JUnit5':

  A: We haven't been able to fix this. The workaround is
  
  1. Launch the tests from Gradle for debugging, as in ``gradlew test --debug-jvm``
  2. Set breakpoint in Eclipse (or the chosen tool)
  3. Launch remote debugging from Eclipse, using port 5005. The debugger will stop in the first breakpoint it finds. In fact, we have an Eclipse launcher already registered, _kpointers, debug gradle tests_

- Q: how do I generate the different primitive pointer classes again?

  A: Run ``scripts\gen_sources.bat`` from the command line

- Q: Regenerating primitive pointers classes fails.
  
  A: Ensure that the ``bin`` directory where ``fmgen.bat`` is located is in the Windows System Path

- Q: how do I use this project within other projects?

  A: you can include the .jar (duh). However, we feel it is easier to do it as follows:
  
  1. Publish it to our local Maven Repository, which we do with ``gradlew publishAllPublicationsToMavenLocalRepository``
  2. Add that repository to our client project, adding ``mavenLocal()`` to the project repositories and then 
  3. Add a dependency to _KPointers_, such as ``implementation("com.softwarementors.kpointers:kpointers:beta-1-SNAPSHOT")``

  Yes, we know, we should publish it to some public Maven repository but meanwhile this will work.
