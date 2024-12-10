package hagem.aoc2024.day9;

import hagem.utils.Reader;

import java.io.File;
import java.util.*;

public class Day9 {

    static class FileSpace {

//        int position;
        int num;
        String value;

//        public FileSpace(int position, int num, String value) {
//            this.position = position;
//            this.num = num;
//            this.value = value;
//        }

        public FileSpace(int num, String value) {
//            this.position = position;
            this.num = num;
            this.value = value;
        }


        @Override
        public String toString() {
            return  num + " " + value;
        }

    }


    File file = new File(Objects.requireNonNull(getClass().getResource("/Day9.data.txt")).getFile());

    private List<FileSpace> parseData(String line) {

        List<FileSpace> retList = new ArrayList<>();

        int id = -1;
        String s;
        int pos = 0;
        FileSpace fileSpace;

        for(int i = 0; i < line.length(); i++) {

            int n = line.charAt(i) - '0';

            if( i % 2 == 0) {
                id++;
//                s = Integer.toString(id);
//                fileSpace = new FileSpace(
//                        pos,
//                        n,
//                        Integer.toString(id)
//                );
                fileSpace = new FileSpace(
                        n,
                        Integer.toString(id)
                );
            } else {
//                s = ".";
//                fileSpace = new FileSpace(
//                        pos,
//                        n,
//                        "."
//                );
                fileSpace = new FileSpace(
                        n,
                        "."
                );
            }

            retList.add(fileSpace);
//            for(int j = 0; j < n; j++) {
//                retList.add(fileSpace);
//            }

            pos += n;

        }

        return retList;

    }

    public String run() {

        List<String> data = Reader.readFile(file);

        List<FileSpace> arr = parseData(data.getFirst());

//        System.out.println(Arrays.toString(arr.toArray()));


        compact(arr);
//        System.out.println(Arrays.toString(arr));

        System.out.println(Arrays.toString(arr.toArray()));


        List<String> list = createString(arr);

        System.out.println(Arrays.toString(list.toArray()));

        long answerP1 = compute(list);

        return "" + answerP1;
    }

    private List<String> createString(List<FileSpace> arr) {

        List<String> ret = new ArrayList<>();

        for(FileSpace fs: arr) {

            for( int i = 0; i < fs.num; i++ ) {

                ret.add(fs.value);

            }


        }

        return ret;

    }

    private void compact(List<FileSpace> arr) {
//
//        int i = 0;
//        int j = arr.size() - 1;

        FileSpace tempFs;

        for(int i = arr.size() - 1; i >= 0; i--){

            if( arr.get(i).value.equals(".") ) {
                continue;
            }

            FileSpace fs =  arr.get(i);

            for(int j = 0; j < i; j++) {

                if(arr.get(j).value.equals(".") &&
                    arr.get(j).num >= fs.num) {

                    tempFs = arr.get(j);

//                    if its equal
                    if( tempFs.num == fs.num ) {
                        tempFs.value = fs.value;

                        fs.value = ".";
                    } else {

                        int tempNum = tempFs.num;
//                        int tempPos = tempFs.position;

                        tempFs.num = fs.num;
                        tempFs.value = fs.value;

//                        fs.num = tempNum;
                        fs.value = ".";

//                        arr.add( j+1,
//                                new FileSpace(tempPos + tempFs.num,
//                                            tempNum - tempFs.num,
//                                        "."
//                                ));

                        arr.add( j+1,
                                new FileSpace(tempNum - tempFs.num,
                                "."
                                ));
                        i++;

                    }

                    if(i + 1 >= arr.size()) {
                        break;
                    }

                    if(arr.get(i + 1).value.equals(".") ) {

                        tempFs = arr.remove(i + 1);
                        fs.num += tempFs.num;

                    }
                    if(arr.get(i - 1).value.equals(".")) {
                        tempFs = arr.remove(i - 1);
                        fs.num += tempFs.num;
                    }

                    if(arr.get(i).num == 0) {
                        arr.remove(i);
                    }

                    break;

                }

            }

        }


//        loop:
//        while(i < j) {
//
//            if(!arr[j].equals(".")) {
//
//                while(!arr[i].equals(".")) {
//                    i++;
//                    if(i >= j){
//                        break loop;
//                    }
//                }
//
//                arr[i] = arr[j];
//                arr[j] = ".";
//
//            }
//
//            j--;
//        }

    }

    private long compute(List<String> arr) {

        long val = 0L;

        for(int i = 0; i < arr.size(); i++) {

            if(arr.get(i).equals(".") ) {
                continue;
            }

            val += i * Long.parseLong(arr.get(i));

        }

        return val;

    }


}
