public class BruteCollinearPoints 
{
   private final LineSegment[] lineSegments;
   private int numberOfSegments = 0;
   private final Point[] pointsArr;
   public BruteCollinearPoints(final Point[] points)// finds all line segments containing 4 points
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
       pointsArr = points;
       LineSegment[] tempResult = new LineSegment[pointsArr.length];
       for (int indP = 0; indP < pointsArr.length - 3; indP++)
       {
           for (int indQ = indP + 1; indQ < pointsArr.length - 2; indQ++)
           {
               for (int indR = indQ + 1; indR < pointsArr.length - 1; indR++)
               {
                   if (pointsArr[indP].slopeTo(pointsArr[indQ]) == pointsArr[indQ].slopeTo(pointsArr[indR]))
                   {
                       for (int indS = indR + 1; indS < pointsArr.length; indS++)
                       {
                          if (pointsArr[indQ].slopeTo(pointsArr[indR]) == pointsArr[indR].slopeTo(pointsArr[indS]))
                           {
                               Point start = pointsArr[indP];
                               Point end = pointsArr[indP];
                               if (start.compareTo(pointsArr[indQ]) > 0)
                                   start = pointsArr[indQ];
                               if (end.compareTo(pointsArr[indQ]) < 0)
                                   end = pointsArr[indQ];
                               
                               if (start.compareTo(pointsArr[indR]) > 0)
                                   start = pointsArr[indR];
                               if (end.compareTo(pointsArr[indR]) < 0)
                                   end = pointsArr[indR];
                               
                               if (start.compareTo(pointsArr[indS]) > 0)
                                   start = pointsArr[indS];
                               if (end.compareTo(pointsArr[indS]) < 0)
                                   end = pointsArr[indS];
                               
                               tempResult[numberOfSegments++] = new LineSegment(start, end);
                           }
                       }
                   }
               }
           }
       }
       lineSegments = new LineSegment[this.numberOfSegments];
       for (int index = 0; index < this.numberOfSegments; index++)
       {
           lineSegments[index] = tempResult[index];
       }
   }
   public int numberOfSegments() { return this.numberOfSegments; }        // the number of line segments
   public LineSegment[] segments() // the line segments
   { 
       final LineSegment[] result = lineSegments;
       return result; 
   }
       
}