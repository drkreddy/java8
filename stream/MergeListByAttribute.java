
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class MergeListByAttribute {

    public static void main(String[] args) {
        A a1 = new A("a1");
        A a2 = new A("a1");
        A a3 = new A("a2");

        B ba = new B("ba");
        B bb = new B("bb");
        B bc = new B("bc");
        a1.bList = Arrays.asList(ba,bb);
        a2.bList = Arrays.asList(ba,bc);
        a3.bList = Arrays.asList(bb,bc);



        List<A> aList = Arrays.asList(a1,a2,a3);
        Map<String, List<A>> map = aList.stream()
                .collect(groupingBy(x -> x.aid));

        for(Map.Entry e :map.entrySet() ){
            System.out.println("key"+e.getKey());
            System.out.println("Value"+e.getValue());
        }

        List<A> mergedList = map.entrySet().stream().map(e -> mergeInsideList(e.getValue())).collect(Collectors.toList());
        for(A a: mergedList){
            System.out.println(a);
        }
    }

    private static A mergeInsideList(List<A> value) {

            Optional<A> result= value.stream().findFirst();
            Set<String> keys = new HashSet<>();
            List<B> merged= new ArrayList<>();
            if(result.isPresent()){
                for(A aa : value){
                   List<B> DtcList = aa.bList.stream().filter(x->{
                       if(!keys.contains(x.id)){
                           keys.add(x.id);
                           return true;
                       }else {
                           return false;
                       }
                   }).collect(Collectors.toList());
                   merged.addAll(DtcList);
                }

            }


        result.get().bList=merged;
        return  result.get();

    }

    static class A{
        public A(String aid) {
            this.aid = aid;
        }

        @Override
        public String toString() {
            return "A{" +
                    "aid='" + aid + '\'' +
                    ", bList=" + bList +
                    '}';
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        String aid;
        List<B> bList;


    }
    static class B{
        public B(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "B{" +
                    "id='" + id + '\'' +
                    '}';
        }

        String id;
    }
}

