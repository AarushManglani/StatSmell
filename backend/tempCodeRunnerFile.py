import jpype
jar_path = "javaparser-core-3.26.1.jar"  # adjust path if inside lib/
jpype.startJVM(classpath=[jar_path])
print("JVM started successfully ✅")
jpype.shutdownJVM()
