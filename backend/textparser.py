from smell_metrics import extract_metrics

java_code = """
class Test {
    int x = 5;
    void foo(int a) {
        for (int i=0; i<a; i++) {
            System.out.println(i);
        }
    }
}
"""
print(extract_metrics(java_code))
