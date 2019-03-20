import java.util.Random;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
public class Quick{
  public static void main(String[]args){
  System.out.println("Size\t\tMax Value\tquick/builtin ratio ");
  int[]MAX_LIST = {1000000000,500,10};
  for(int MAX : MAX_LIST){
    for(int size = 31250; size < 2000001; size*=2){
      long qtime=0;
      long btime=0;
      //average of 5 sorts.
      for(int trial = 0 ; trial <=20; trial++){
        int []data1 = new int[size];
        int []data2 = new int[size];
        for(int i = 0; i < data1.length; i++){
          data1[i] = (int)(Math.random()*MAX);
          data2[i] = data1[i];
        }
        long t1,t2;
        t1 = System.currentTimeMillis();
        Quick.quicksort(data2);
        t2 = System.currentTimeMillis();
        qtime += t2 - t1;
        t1 = System.currentTimeMillis();
        Arrays.sort(data1);
        t2 = System.currentTimeMillis();
        btime+= t2 - t1;
        if(!Arrays.equals(data1,data2)){
          System.out.println("FAIL TO SORT!");
          System.exit(0);
        }
      }
      System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
    }
    System.out.println();
  }
  }

   public static void quicksort(int[] data){
    try{
      quicksortHelp(data, 0, data.length - 1);
    }catch (IllegalArgumentException e){
      //if the array is size 0, don't do anything to it
    }

   }
   private static void quicksortHelp(int[] data, int start, int end){ //here start and end represent beginning of array to be processed and end
     if (start<=end){
       if (end - start < 47){
         insertionSub(data, start, end);
       }else{
         int pivotInd = partition(data, start, end);
         quicksortHelp(data, start, pivotInd-1); //less than half, don't wanna touch pivot
         quicksortHelp(data, pivotInd+1, end); //don't wanna affect pivot, point before start is swapped w pivot -->NVM UPDATED SO START IS MOVED UP AND START INDEX MADE PIVOT
       }
      }
   }
   //for dutch testing
   /*
   public static void quicksortD(int[] data){
    try{
      quicksortHelpD(data, 0, data.length - 1);
    }catch (IllegalArgumentException e){
      //if the array is size 0, don't do anything to it
    }

   }
   private static void quicksortHelpD(int[] data, int start, int end){ //here start and end represent beginning of array to be processed and end
     //System.out.println("In new qshD call. START: " + start + " END: " + end);
     if (start<=end){
       //System.out.println("\nSTART: " + start + " END: " + end);
       int[] newSandEs = partitionDutch(data, start, end);
       quicksortHelpD(data, start, newSandEs[0]); //less than half, don't wanna touch pivot
       //System.out.println("branching");
       quicksortHelpD(data, newSandEs[1], end); //don't wanna affect pivot, point before start is swapped w pivot -->NVM UPDATED SO START IS MOVED UP AND START INDEX MADE PIVOT
     }
   }
   */
   //returns which element is at kth index in sorted array
   public static int quickselect(int[] data, int k){
     if (k >= data.length || k < 0){
       throw new IllegalArgumentException("k must be in range");
     }
     if (data.length == 0){
       throw new IllegalArgumentException("data must have elements");
     }
     int start = 0;
     int end = data.length - 1;
     boolean done = false; //just fake recursion
     while (!done){
       int mayK = partition(data, start, end); //get an index from partitioning
       if (mayK == k){ //if it's the right one then return
         return data[mayK];
       }
       if (k>mayK){ //if it's greater than, partition just the half after that
         start = mayK+1;
         end = end;
       }else{ //if it's less, partition everything before mayK
         start = start;
         end = mayK-1;
       }
     }
     return -1; //failed
   }
   //chooses pivot smartly
   public static int partition(int[] data, int start, int end){
      if (data.length == 0){
        throw new IllegalArgumentException("can't process empty data");
      }
      if (start == end){
        return start;
      }
      Random rng = new Random();
      int pivotInd;
      int pivot;
      //decide pivot index
      if (data[start] > data[end] && data[start] < data[(end+start)/2] || data[start] < data[end] && data[start] > data[(end+start)/2]){
        pivot = data[start];
        pivotInd = start;
      }else if (data[end] > data[start] && data[end] < data[(end+start)/2] || data[end] < data[start] && data[end] > data[(end+start)/2]){
        pivot = data[end];
        pivotInd = end;
      }else{
        pivotInd = (end+start)/2;
        pivot = data[pivotInd];
      }
      int pivotAtStartInd = start;
      swap(data, pivotAtStartInd, pivotInd);
      start++;
      //what you can do for dutch partitioning is if data[start] == pivot,  swap(data, data[start], data[nextAvailPivotInd]) and then do start++
      while (start != end){
        int dealWDupes = rng.nextInt(2);
        if (data[start] > pivot || data[start] == pivot && dealWDupes == 0){
          swap(data, start, end);
          end--;
        }else{
          start++;
        }
      }
      if (data[start] < pivot){
        swap(data, start, pivotAtStartInd);
        return start;
      }else{
        swap(data, start-1, pivotAtStartInd);
        return start-1;
      }
    }
    //dutch
    //nvm j not gonna do this
    /*
    public static int[] partitionDutch(int[] data,int start, int end){
      if (data.length == 0){
        throw new IllegalArgumentException("can't process empty data");
      }
      if (start == end){
        throw new IllegalArgumentException("can't partition if start == end");
      }
      int initStart = start;
      int initEnd = end;
      int pivotInd; //end is gt, start is i and duplicateSpaceAvail is lt
      int pivot;
      int pivotAtStartInd;
      int duplicateSpaceAvail;
      //assign pivot & move to back
      if (data[start] > data[end] && data[start] < data[(end+start)/2] || data[start] < data[end] && data[start] > data[(end+start)/2]){
        pivot = data[start];
        pivotInd = start;
      }else if (data[end] > data[start] && data[end] < data[(end+start)/2] || data[end] < data[start] && data[end] > data[(end+start)/2]){
        pivot = data[end];
        pivotInd = end;
      }else{
        pivotInd = (end+start)/2;
        pivot = data[pivotInd];
      }
      //System.out.println("\nPIVOT: " + pivot + " PIVIND: " + pivotInd);
      //System.out.println("ARRAY LOOKS: " + Arrays.toString(data));
      pivotAtStartInd = start;
      swap(data, pivotAtStartInd, pivotInd);
      duplicateSpaceAvail = pivotAtStartInd + 1;
      start++;
      // if an element is = to pivot, add it to the front like pivot. then at end, for the length of the duplicates at the end, swap them
      // w the low elements. shouldn't be a problem where u ht the high elements
      while (start != end){
        if (data[start] > pivot){
          swap(data, start, end); //if bigger, move to behind where pivot will eventually be placed
          end--;
          //System.out.println("MOVED: " + Arrays.toString(data));
        }else if (data[start] == pivot){
          swap(data, start, duplicateSpaceAvail); //if same size, switch w whatever is at duplicatseSpaceAvail, then move that index up
          duplicateSpaceAvail++;
          start++; //cuz duplicateSpaceAvail is behind start, so whatever is switiched into start position will have already been sorted
          //System.out.println("MOVED: " + Arrays.toString(data));
        }else{
          start++;
          //System.out.println("MOVED: " + Arrays.toString(data));
        }
      }
      //now swap everything that's a duplicate w the #s before START
      //System.out.println("\nNOW RETURNING PIVOTS TO AROUND PIVOT'S RIGHT INDEX\n");
      if (data[start] < pivot){
        swap(data, start, pivotAtStartInd);
        //System.out.println("RETURN: " + Arrays.toString(data));
        //filling space between 0 (will have start or start-1 val) and non-duplicates, which will be all the duplicates, w non-duplicateSpaceAvail
        // and moving duplicates to behind the pivot which is now at start or start-1
        //note: if there aren't any duplicates, this will just jumble the start section
        int j = 1; //will be increased bellow
        for (int i = start-1; i>=duplicateSpaceAvail; i--){
          swap(data, j, i);
        //  System.out.println("RETURN: " + Arrays.toString(data));
          j++;
        }
        //wherever j stops is the upper bound of the less than pivot part
        j--;
        int[] toReturn = {j, end};
        //System.out.println("end of lower and beginning of higher: " + Arrays.toString(toReturn));
        return toReturn;
      }else{
        swap(data, start-1, pivotAtStartInd);
        //System.out.println("RETURN: " + Arrays.toString(data));
        //filling space between 0 (will have start or start-1 val) and non-duplicates, which will be all the duplicates, w non-duplicateSpaceAvail
        // and moving duplicates to behind the pivot which is now at start or start-1
        int l = 1;
        //actually idt start section will be jumbled
        for (int k = (start-2); k>=duplicateSpaceAvail; k--){
          swap(data, l, k);
          //System.out.println("RETURN: " + Arrays.toString(data));
          l++;
        } //wherever k stops is the upper bound of the less than pivot part ()
        l--;
        int[] toReturn = {l, end}; //end is lower bound of the greater than part, upperbound is the initEnd
                                   //k is upper bound of the less than part, lowerbound is the initStart
        //System.out.println("end of lower and beginning of higher: " + Arrays.toString(toReturn));
        return toReturn;
      }

    }
    */
    //for testing
    private static void swap(int[] data, int ind1, int ind2){
      int temp = data[ind1];
      data[ind1] = data[ind2];
      data[ind2] = temp;
    }
    private static boolean sortedIsh(int[] data, int pivotInd){
      for (int i = 0; i<pivotInd; i++){
        if (data[i] > data[pivotInd]){
          System.out.println("data[" + i + "]: " + data[i] + " is not less than or equal to " + data[pivotInd]);
          return false;
        }
      }
      for (int iA = pivotInd+1; iA<data.length; iA++){
        if (data[iA] < data[pivotInd]){
          System.out.println("data[" + iA + "]: " + data[iA] + " is not greater than or equal to " + data[pivotInd]);
          return false;
        }
      }
      return true;
    }
    public static void insertionSub(int[] data, int lo, int hi){
      for (int ind = lo+1; ind <= hi; ind++){
        int current = data[ind];
        int j = ind-1;
        if (current > data[j]){
          //don't do anything, current is in the right place
        }else{
          while (j >= lo && data[j] > current){
            j--; //will return the first index of an element less than current. so insert infront of that element
          }
          insert(data, ind, j+1); //so if j is -1, bc it's less than everything, move it to 0
        }
      }
    }
    //put ind1 at ind2, move everything between ind1 and ind2 up one
    //ind1 is the greater value
    private static void insert(int[] data, int ind1, int ind2){
      //System.out.println("inserting: " + data[ind1] + "(index " + ind1 + " )" + "at index " + ind2);
      int temp = data[ind1];
      for (int i = ind1; i>ind2; i--){
        //System.out.println(Arrays.toString(data));
        data[i] = data[i-1];
      }
      data[ind2] = temp;
    }
 }
