import edu.princeton.cs.algs4.StdOut;
public class FastCollinearPoints 
{
    private int segmentsCount = 0;
    private LineSegment[] lineSegments;
    
    private class Node
    {
        public Point first;
        public Point last;
        
        public Node(Point first, Point last)
        {
            this.first = first;
            this.last = last;
        }
    }
    
    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        if (points == null)
            throw (new java.lang.NullPointerException());
        for (int index = 0; index < points.length; index++)
        {
            if (points[index] == null)
                   throw (new java.lang.NullPointerException());
            for (int index2 = index + 1; index2 < points.length; index2++)
            {
                if (points[index2] == null)
                   throw (new java.lang.NullPointerException());
                if (points[index].compareTo(points[index2]) == 0)
                    throw (new java.lang.IllegalArgumentException());
            }
        }
        Node[] lines = new Node[points.length]; 
        Point[] auxArray = java.util.Arrays.copyOf(points, points.length);
        for (int index = 0; index < points.length; index++) //index of base point to determine angle 
        {
            java.util.Arrays.sort(auxArray, points[index].slopeOrder());
            int currentStreak = 2;
            for (int auxIndex = 2; auxIndex < auxArray.length; auxIndex++) //loop through the array of points sorted by slope with base
            {//find series of equal slopes and add them to resulting array if it's unique
                double thisSlope = points[index].slopeTo(auxArray[auxIndex]);
                double prevSlope = points[index].slopeTo(auxArray[auxIndex - 1]);
                if (thisSlope == prevSlope)
                {//if slopes of two points are equal - they're lying on one line
                    currentStreak++;
                }
                else
                {
                    if (currentStreak > 3)
                    {
                        Point first = points[index];
                        Point last = points[index];
                        for (int subIndex = 1; subIndex < currentStreak; subIndex++)
                        {
                            if (first.compareTo(auxArray[auxIndex - subIndex]) < 0)
                                first = auxArray[auxIndex - subIndex];
                            if (last.compareTo(auxArray[auxIndex - subIndex]) > 0)
                                last = auxArray[auxIndex - subIndex];
                        }
                        if (segmentsCount == lines.length - 1)
                            lines = java.util.Arrays.copyOf(lines, lines.length*2);
                        lines[segmentsCount++] = new Node(first, last);
                    }
                    currentStreak = 2;
                }
            }
            if (currentStreak > 3)
            {
                Point first = points[index];
                Point last = points[index];
                for (int subIndex = 1; subIndex < currentStreak; subIndex++)
                {
                    if (first.compareTo(auxArray[auxArray.length - subIndex]) < 0)
                        first = auxArray[auxArray.length - subIndex];
                    if (last.compareTo(auxArray[auxArray.length - subIndex]) > 0)
                        last = auxArray[auxArray.length - subIndex];
                }
                if (segmentsCount == lines.length - 1)
                    lines = java.util.Arrays.copyOf(lines, lines.length*2);
                lines[segmentsCount++] = new Node(first, last);
            }
        }
      // remove duplicates
        lineSegments = new LineSegment[lines.length];
        int totalLines = 0;
        mergesort(lines, 0, segmentsCount - 1);        
        Node thisNode = null;
        for (int index = 0; index < segmentsCount; index++)
        {
           if (thisNode == null)
           {
               lineSegments[totalLines++] = new LineSegment(lines[index].first, lines[index].last);
               thisNode = lines[index];
               StdOut.print("added line: ");
                   StdOut.println(lineSegments[totalLines - 1].toString());
           }
           else
           {
               if (thisNode.first.compareTo(lines[index].first) == 0 && thisNode.last.compareTo(lines[index].last) == 0)
               {
                   continue;
               }
               else
               {
                   lineSegments[totalLines++] = new LineSegment(lines[index].first, lines[index].last);
                   thisNode = lines[index];
                   StdOut.print("added line: ");
                   StdOut.println(lineSegments[totalLines - 1].toString());
               }
           }
        }
        segmentsCount = totalLines;
}
    
    private void mergesort(Node[] array, int firstIndex, int lastIndex)
    {
            mergesortfirst(array, firstIndex, lastIndex);
            mergesortlast(array, firstIndex, lastIndex);
    }
    
    private void mergesortfirst(Node[] array, int firstIndex, int lastIndex)
    {
        if (lastIndex - firstIndex < 1)
            return;
        else
        {
            int middle = (lastIndex - firstIndex) / 2 + firstIndex;
            mergesortfirst(array, firstIndex, middle);
            mergesortfirst(array, middle + 1, lastIndex);
            mergefirst(array, firstIndex, middle, lastIndex);
        }
    }
    
    private void mergefirst(Node[] array, int firstIndex, int middle, int lastIndex)
    {
        int left = firstIndex;
        int right = middle + 1;
        Node[] aux = new Node[(lastIndex + 1) - firstIndex];
        int auxIndex = 0;
        while (middle - left >= 0 && lastIndex - right >= 0)
        {
            if (array[left].first.compareTo(array[right].first) > 0)
                aux[auxIndex++] = array[right++];
            else
                aux[auxIndex++] = array[left++];
        }
        while(middle - left >= 0)
            aux[auxIndex++] = array[left++];
        while(lastIndex - right >= 0)
            aux[auxIndex++] = array[right++];
        auxIndex = 0;
        for(int index = firstIndex; index <= lastIndex; index++)
        {
            array[index] = aux[auxIndex++];
        }
    }
    
    private void mergesortlast(Node[] array, int firstIndex, int lastIndex)
    {
        if (lastIndex - firstIndex < 1)
            return;
        else
        {
            int middle = (lastIndex + firstIndex) / 2;
            mergesortlast(array, firstIndex, middle);
            mergesortlast(array, middle + 1, lastIndex);
            mergelast(array, firstIndex, middle, lastIndex);
        }
    }
    
    private void mergelast(Node[] array, int firstIndex, int middle, int lastIndex)
    {
        int left = firstIndex;
        int right = middle + 1;
        Node[] aux = new Node[lastIndex + 1 - firstIndex];
        int auxIndex = 0;
        while (middle - left >= 0 && lastIndex - right >= 0)
        {
            if (array[left].last.compareTo(array[right].last) > 0)
                aux[auxIndex++] = array[right++];
            else
                aux[auxIndex++] = array[left++];
        }
        while(middle - left >= 0)
            aux[auxIndex++] = array[left++];
        while(lastIndex - right >= 0)
            aux[auxIndex++] = array[right++];
        auxIndex = 0;
        for(int index = firstIndex; index <= lastIndex; index++)
        {
            array[index] = aux[auxIndex++];
        }
    }
    
    public static void main(String args[])
    {
        //String arg = args[0];
        int[] xpoints = new int[]{5000,10000,30000,20000,25000,30000,1234,10000,15000,20000,25000,30000,27000,26000,20000,19000,18000,0,2300,4600,11500,5678,0,0,0,0,0,0,0};
        int[] ypoints = new int[]{0,0,3,0,0,0,5678,3100,6200,9300,12400,15700,7500,10000,25000,27500,30000,0,4100,8200,20500,4321,30000,25000,20000,15000,11000,10000,5000};
        Point[] newpoints = new Point[29];
        for (int index = 0; index < 29; index++)
        {
            newpoints[index] = new Point(xpoints[index], ypoints[index]);
        }
        FastCollinearPoints col = new FastCollinearPoints(newpoints);
    }
    
    public int numberOfSegments() { return segmentsCount; }        // the number of line segments
    public LineSegment[] segments()               // the line segments
    {
        return java.util.Arrays.copyOf(lineSegments, segmentsCount);
    }
}