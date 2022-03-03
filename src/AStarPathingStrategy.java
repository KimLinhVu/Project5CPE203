import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;

class AStarPathingStrategy implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();

        PriorityQueue<Node> openListQueue = new PriorityQueue<>();
        HashMap<Point, Node> openListHash = new HashMap<>();
        HashMap<Point, Node> closedList = new HashMap<>();

        Node startNode = new Node(start, 0);
        startNode.setH(distance(start, end));
        startNode.setF(startNode.getH());

        openListQueue.add(startNode);
        openListHash.put(start, startNode);

        while(!openListQueue.isEmpty()){

            Node currNode = openListQueue.peek();

            if (withinReach.test(currNode.getLocation(), end)){

                while (currNode.getPrevNode() != null){
                    path.add(currNode.getLocation());
                    currNode = currNode.getPrevNode();
                }

                Collections.reverse(path);
                return path;
            }

            List<Point> neighbors = potentialNeighbors.apply(currNode.getLocation())
                    .filter(canPassThrough).collect(Collectors.toList());

            for (Point point : neighbors){

                if (!closedList.containsKey(point)){

                    double g = 1 + currNode.getG();
                    Node node = new Node(point, g);

                    if (openListHash.containsKey(point)){
                        if (openListHash.get(point).getG() > g){
                            node.setPrevNode(currNode);
                            node.setF(node.getG() + node.getH());

                            openListQueue.remove(openListHash.get(point));
                            openListHash.remove(point);

                            openListHash.put(point, node);
                            openListQueue.add(node);
                        }
                    }
                    else{
                        node.setH(distance(point, end));
                        node.setF(node.getG() + node.getH());
                        node.setPrevNode(currNode);

                        if (openListHash.containsKey(point)){
                            openListQueue.remove(openListHash.get(point));
                            openListHash.remove(point);
                        }
                        openListHash.put(point, node);
                        openListQueue.add(node);
                    }

                }
            }
            openListQueue.remove(currNode);
            closedList.put(currNode.getLocation(), currNode);
        }

        return path;
    }

    public double distance(Point start, Point end){
        return Math.hypot(start.getX() - end.getX(), start.getY() - end.getY());
    }
}
