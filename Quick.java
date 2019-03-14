import java.util.Random;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
public class Quick{
  public static void main(String[] args){
    for (int i = 0; i<3; i++){
      Random rng = new Random();
      int size = rng.nextInt(15) + 2; //so no negatives
      int[] data = new int[size];
      int[] dataSorted = new int[size];
      for (int c = 0; c<size; c++){
        int toAdd = rng.nextInt(5);
        data[c] = toAdd;
        dataSorted[c] = toAdd;
      }
     Arrays.sort(dataSorted);
     System.out.println("size: " + size);
     System.out.println("Array: " + Arrays.toString(data));
     //System.out.println("the lt and gt: " + Arrays.toString(partitionDutch(data, 0, (data.length-1))));
     quicksortD(data);
     System.out.println("Sorted: " + Arrays.toString(dataSorted) + "\nDutched: " + Arrays.toString(data));
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
       int pivotInd = partition(data, start, end);
       quicksortHelp(data, start, pivotInd-1); //less than half, don't wanna touch pivot
       quicksortHelp(data, pivotInd+1, end); //don't wanna affect pivot, point before start is swapped w pivot -->NVM UPDATED SO START IS MOVED UP AND START INDEX MADE PIVOT
     }
   }
   //for dutch testing
   public static void quicksortD(int[] data){
    try{
      quicksortHelpD(data, 0, data.length - 1);
    }catch (IllegalArgumentException e){
      //if the array is size 0, don't do anything to it
    }

   }
   private static void quicksortHelpD(int[] data, int start, int end){ //here start and end represent beginning of array to be processed and end
     if (start<=end){
       int[] newSandEs = partitionDutch(data, start, end);
       quicksortHelpD(data, start, newSandEs[0]); //less than half, don't wanna touch pivot
       quicksortHelpD(data, newSandEs[1], end); //don't wanna affect pivot, point before start is swapped w pivot -->NVM UPDATED SO START IS MOVED UP AND START INDEX MADE PIVOT
     }
   }
   //returns which element is at kth index in sorted array
   public static int quickselect(int[] data, int k){
     if (data.length > 1){
       boolean foundK = false;
       while (!foundK){
         int ind = partitionRandom(data, 0, data.length-1); //use the random so no infinite loop (non-random will give same pivot over and over)
         if (ind == k){
           foundK = true;
           return data[ind];
         }
       }
       return -1;
     }else{
       return data[0];
     }
   }
   //chooses pivot randomly for quickselect
   public static int partitionRandom(int[] data, int start, int end){
     if (data.length == 0){
       throw new IllegalArgumentException("can't process empty data");
     }
     if (start == end){
       return start; //don't touch the array
     }
     Random rng = new Random();
     int pivotInd = rng.nextInt(data.length);
     int pivot = data[pivotInd];
     int pivotAtStartInd = start;
     swap(data, pivotAtStartInd, pivotInd);//move pivot to back
     start++;
     while (start != end){//when you still have left to compare
       int dealWDupes = rng.nextInt(2);
       if (data[start] > pivot || data[start] == pivot && dealWDupes == 0){//compare
         swap(data, start, end);//either move start to end and move end
         end--;
       }else{
         start++;
       }
     }
     if (data[start] < pivot){ //you need to switch the start and pivot
       swap(data, start, pivotAtStartInd);
        return start;
     }else{
       swap(data, start-1, pivotAtStartInd);
       return start-1;
     }
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
      //System.out.println("\n\nPIVOT: " + pivot + " PIVIND: " + pivotInd);
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
    //  System.out.println("\nNOW RETURNING PIVOTS TO AROUND PIVOT'S RIGHT INDEX\n");
      if (data[start] < pivot){
        swap(data, start, pivotAtStartInd);
        //System.out.println("RETURN: " + Arrays.toString(data));
        //filling space between 0 (will have start or start-1 val) and non-duplicates, which will be all the duplicates, w non-duplicateSpaceAvail
        // and moving duplicates to behind the pivot which is now at start or start-1
        //note: if there aren't any duplicates, this will just jumble the start section
        int j = 1; //will be increased bellow
        for (int i = start-1; i>=duplicateSpaceAvail; i--){
          swap(data, j, i);
          //System.out.println("RETURN: " + Arrays.toString(data));
          j++;
        }
        //wherever j stops is the upper bound of the less than pivot part
        j--;
        int[] toReturn = {j, end};
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
        return toReturn;
      }

    }
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
    private static void insertionSort(int[] ary){
      for (int i = 1; i<ary.length; i++){ //for every int in the array, ignoring the first
        int current = ary[i]; //make a variable w that value
        int j = i-1; //make another value that will allow us to get the value right before it
        while ( j >= 0 && ary[j] > current){ //we're gonna decrease j -- while every index is greater than current
          ary[j+1] = ary[j]; //move them up one (at first, there will be 2 of ary[j], doesn't matter since current is stored;)
          j--; //it'll go like abcc to abbc to aabc, finally once either you reach 0 or re no longer getting values less than current
        }
         //if not at end, you stopped cuz something is smaller. put it one ahead of the smaller one. this is ok because of the aabc pattern
        ary[j+1] = current;

      }
    }
 }
