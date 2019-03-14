import java.util.Random;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
public class Quick{
  public static void main(String[] args){
    for (int i = 0; i<25; i++){
      Random rng = new Random();
      int size = rng.nextInt(10) + 1; //so no negatives
      int[] data = new int[size];
      int[] dataSorted = new int[size];
      for (int c = 0; c<size; c++){
        int toAdd = rng.nextInt(50000);
        data[c] = toAdd;
        dataSorted[c] = toAdd;
      }
     Arrays.sort(dataSorted);
     System.out.println("size: " + size);
     int k = rng.nextInt(size);
     if (dataSorted[k] == quickselect(data, k)){
       System.out.println("TRUE!");
     }else{
       System.out.println("FALSE");
     }
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
