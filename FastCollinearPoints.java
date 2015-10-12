import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints 
{
    private int segmentsCount = 0;
    private final LineSegment[] lineSegments;
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
        LineSegment[] tempResult = new LineSegment[points.length];
        for (int index = 0; index < points.length; index++)
        {
            int comboCount = 1;
            int offset = 1;
            Point[] tempInput = points;
            java.util.Arrays.sort(tempInput, points[index].slopeOrder());
            for (int index2 = 1; index2 < tempInput.length; index2++)
            {
                if (points[index].compareTo(tempInput[index2]) == 0 && index2 > 2 && index2 < tempInput.length - 1)
                { 
                    index2++;
                    offset = 2;
                }
                if (points[index].slopeTo(tempInput[index2]) == points[index].slopeTo(tempInput[index2 - offset]))
                {
                    offset = 1;
                    comboCount++;
                }
                else
                {
                    if (comboCount > 2)
                    {
                        Point start = points[index];
                        Point end = points[index];
                        for (int index3 = 1; index3 <= comboCount; index3++)
                        {
                            if (points[index].compareTo(tempInput[index2 - index3]) == 0)
                            {
                                index3++;
                                comboCount++;
                            }
                            if (start.compareTo(tempInput[index2 - index3]) > 0)
                                start = tempInput[index2 - index3];
                            if (end.compareTo(tempInput[index2 - index3]) < 0)
                                end = tempInput[index2 - index3];
                        }
                        boolean isDuplicate = false;
                        for (int duplicatesIndex = 0; duplicatesIndex <= segmentsCount; duplicatesIndex++)
                        {
                           // start.compareTo();
                        }
                        if (!isDuplicate)
                            tempResult[segmentsCount++] = new LineSegment(start, end);
                    }
                    comboCount = 1;
                }
            }
        }
        lineSegments = new LineSegment[segmentsCount];
        for (int index = 0; index < segmentsCount; index++)
            lineSegments[index] = tempResult[index];
    }

    public int numberOfSegments() { return segmentsCount; }        // the number of line segments
    public LineSegment[] segments()               // the line segments
    {
        final LineSegment[] result = lineSegments;
        return result;
    }
    private  void main(String[] args)
    {
        
    }
}