public class FastCollinearPoints 
{
    private int segmentsCount = 0;
    private final LineSegment[] lineSegments;
    
    private class Node
    {
        public Point first;
        public Point last;
        public Node next;
        
        public Node(Point first, Point last)
        {
            this.first = first;
            this.last = last;
        }
    }
    
    Node firstNode = null;
    
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
        Point[] auxArray = java.util.Arrays.copyOf(points, points.length);
        for (int index = 0; index < points.length; index++) //index of base point to determine angle 
        {
            java.util.Arrays.sort(auxArray, points[index].slopeOrder());
            int currentStreak = 2;
            for (int auxIndex = 2; auxIndex < auxArray.length; auxIndex++) //loop through the array of points sorted by slope with base
            {//find series of equal slopes and add them to resulting array if it's unique
                if (points[index].slopeTo(auxArray[auxIndex]) == points[index].slopeTo(auxArray[auxIndex - 1]))
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
                        if (firstNode == null)
                        {
                            segmentsCount++;
                            firstNode = new Node(first, last);
                        }
                        else
                        {
                            boolean isUnique = true;
                            Node thisNode = firstNode;
                            while (thisNode.next != null)
                            {
                                if (thisNode.first == first && thisNode.last == last)
                                {
                                    isUnique = false;
                                    break;
                                }
                                thisNode = thisNode.next;
                            }
                            if (isUnique && thisNode.next == null)
                            {
                                if (thisNode.first != first || thisNode.last != last)
                                {
                                    segmentsCount++;
                                    thisNode.next = new Node(first, last);
                                }
                            }
                        }
                            
                    }
                    currentStreak = 2;
                }
            }
        }
        Node thisNode = firstNode;
        lineSegments = new LineSegment[segmentsCount];
        for (int index = 0; index < lineSegments.length; index++)
        {
            lineSegments[index] = new LineSegment(thisNode.first, thisNode.last);
            thisNode = thisNode.next;
        }
    }

    public int numberOfSegments() { return segmentsCount; }        // the number of line segments
    public LineSegment[] segments()               // the line segments
    {
        return java.util.Arrays.copyOf(lineSegments, lineSegments.length);
    }
}