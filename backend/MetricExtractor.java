import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;

import java.util.*;
import java.io.*;

public class MetricExtractor {

    public static double[] extractMetrics(String code) {
        try {
            CombinedTypeSolver typeSolver = new CombinedTypeSolver();
            typeSolver.add(new ReflectionTypeSolver());
            JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
            StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

            CompilationUnit cu = StaticJavaParser.parse(code);
            Collector c = new Collector();
            c.visit(cu, null);

            double fanOut = c.methodCalls.values().stream().mapToInt(Set::size).max().orElse(0);
            double fanIn = 0;
            Map<String, Integer> freq = new HashMap<>();
            for (Set<String> set : c.methodCalls.values()) {
                for (String m : set) {
                    freq.put(m, freq.getOrDefault(m, 0) + 1);
                }
            }
            fanIn = freq.values().stream().mapToInt(i -> i).max().orElse(0);

            return new double[] {
                c.noc, c.eloc, c.nmnoparam, c.prm, c.pmmm, c.wmc, c.noa,
                c.lcom, c.cyclo, c.nopa, fanIn, fanOut
            };

        } catch (Exception e) {
            e.printStackTrace();
            return new double[12];
        }
    }

    private static class Collector extends VoidVisitorAdapter<Void> {
        double noc = 0, eloc = 0, nmnoparam = 0, prm = 0, pmmm = 0;
        double wmc = 0, noa = 0, lcom = 0, cyclo = 0, nopa = 0;
        Map<String, Set<String>> methodCalls = new HashMap<>();

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            noc++;
            noa += n.getFields().size();
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration m, Void arg) {
            wmc++;
            int params = m.getParameters().size();
            if (params == 0) nmnoparam++;
            prm += params;
            cyclo += 1 + m.findAll(IfStmt.class).size()
                         + m.findAll(ForStmt.class).size()
                         + m.findAll(WhileStmt.class).size()
                         + m.findAll(DoStmt.class).size()
                         + m.findAll(SwitchEntry.class).size();
            String name = m.getNameAsString();
            methodCalls.putIfAbsent(name, new HashSet<>());
            super.visit(m, arg);
        }

        @Override
        public void visit(MethodCallExpr call, Void arg) {
            try {
                ResolvedMethodDeclaration resolved = call.resolve();
                String sig = resolved.getQualifiedSignature();
                for (String key : methodCalls.keySet()) {
                    methodCalls.get(key).add(sig);
                }
            } catch (Exception ignored) { }
            super.visit(call, arg);
        }

        @Override
        public void visit(FieldDeclaration fd, Void arg) {
            eloc += fd.getVariables().size();
            super.visit(fd, arg);
        }

        @Override
        public void visit(VariableDeclarationExpr vde, Void arg) {
            eloc += vde.getVariables().size();
            super.visit(vde, arg);
        }

        @Override
        public void visit(ReturnStmt r, Void arg) {
            pmmm++;
            super.visit(r, arg);
        }

        @Override
        public void visit(BlockStmt bs, Void arg) {
            eloc += bs.getStatements().size();
            super.visit(bs, arg);
        }
    }

    public static void main(String[] args) throws Exception {
        String code = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("Test.java")));
        System.out.println(Arrays.toString(extractMetrics(code)));
    }
}
