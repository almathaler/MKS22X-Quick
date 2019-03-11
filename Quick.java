import java.util.Random;
import java.util.Arrays;
public class Quick{
  public static void main(String[] args){
    for (int i = 0; i<25; i++){
      Random rng = new Random();
      int[] data = new int[25];
      for (int c = 0; c<25; c++){
        data[c] = rng.nextInt(5000);
      }
      System.out.println(Arrays.toString(data));
      int pivotInd = partition(data, 1, data.length-1);
      System.out.println("correctly partitioned?: " + sortedIsh(data, pivotInd));
    }
    //System.out.println(partition(data, 1, data.length-1));
  }
  /*return the value that is the kth smallest value of the array.
 */
 public static int quickselect(int[] data, int k){
   boolean foundK = false;
   while (!foundK){
     int ind = partition(data, 1, data-1);
     if (ind = k){
       return data[ind];
     }
   }
 }



  //start = 1 and end = data.length-1
  private static int partition(int[] data, int start, int end){
    Random rng = new Random();
    int pivotInd = rng.nextInt(data.length);
    int pivot = data[pivotInd];
    swap(data, 0, pivotInd);//move pivot to back
    while (start != end){//when you still have left to compare
      if (data[start] > pivot){//compare
        swap(data, start, end);//either move start to end and move end
        end--;
      }else{
        start++;//or don't do anything, move start foward
      }
    }
    if (data[start] < pivot){ //you need to switch the start and pivot
      swap(data, start, 0);
      //System.out.println("Final index of " + pivot + " is ");
      return start;
    }else{
      swap(data, start-1, 0);
    //  System.out.println("Final index of " + pivot + " is ");
      return start-1;
    }
  }
  private static void swap(int[] data, int ind1, int ind2){
    int temp = data[ind1];
    data[ind1] = data[ind2];
    data[ind2] = temp;
  }
  private static boolean sortedIsh(int[] data, int pivotInd){
    for (int i = 0; i<pivotInd; i++){
      if (data[i] > data[pivotInd]){
        return false;
      }
    }
    for (int iA = pivotInd+1; iA<data.length; iA++){
      if (data[iA] < data[pivotInd]){
        return false;
      }
    }
    return true;
  }
}
