import java.util.*;

public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly
   * shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints
   * nothing.
   *
   * @param vertex the starting vertex
   * @param k      the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    if (vertex == null) return;

    Set<Vertex<String>> visited = new HashSet<>();
    printShortWords(vertex, visited, k);
  }

  private static void printShortWords(Vertex<String> current, Set<Vertex<String>> visited, int k) {
    if (visited.contains(current)) return;
    visited.add(current);

    String value = current.data;
    if(value.length() < k) {
      System.out.println(value);
    }

    for (Vertex<String> neighbor : current.neighbors) {
      printShortWords(neighbor, visited, k);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own
   * value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if (vertex == null) return "";

    Set<Vertex<String>> visited = new HashSet<>();
    return longestWord(vertex, visited, "");
  }

  private static String longestWord(Vertex<String> current, Set<Vertex<String>> visited, String currentLongest) {
    if (visited.contains(current)) {
      return currentLongest;
    }
    visited.add(current);

    String value = current.data;
    if (value.length() > currentLongest.length()) {
      currentLongest = value;
    }

    for (Vertex<String> neighbor : current.neighbors) {
      currentLongest = longestWord(neighbor, visited, currentLongest);
    }
    return currentLongest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex
   * and
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T>    the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    if (vertex == null) return;

    Set<Vertex<T>> visited = new HashSet<>();
    printSelfLoopers(vertex, visited);
  }

  private static <T> void printSelfLoopers(Vertex<T> current, Set<Vertex<T>> visited) {
    if (visited.contains(current)) return;
    visited.add(current);

    if (current.neighbors.contains(current)) {
      System.out.println(current.data);
    }

    for (Vertex<T> neighbor : current.neighbors) {
      printSelfLoopers(neighbor, visited);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a
   * series of flights
   * starting from the given airport. If the start and destination airports are
   * the same, returns true.
   *
   * @param start       the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    if (start == null || destination == null) {
      return false;
    }
    if (start.equals(destination)) {
      return true;
    }

    Set<Airport> visited = new HashSet<>();
    return canReach(start, destination, visited);
  }

  private static boolean canReach(Airport current, Airport destination, Set<Airport> visited) {
    if (current.equals(destination)) {
      return true;
    }
    visited.add(current);

    for (Airport next : current.getOutboundFlights()) {
      if (!visited.contains(next)) {
        if (canReach(next, destination, visited)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the
   * given starting value.
   * The graph is represented as a map where each vertex is associated with a list
   * of its neighboring values.
   *
   * @param graph    the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T>      the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    if (graph == null || !graph.containsKey(starting)) {
      return (graph == null)
          ? new HashSet<>()
          : new HashSet<>(graph.keySet());
    }

    Set<T> visited = new HashSet<>();
    unreachable(starting, graph, visited);

    Set<T> result = new HashSet<>(graph.keySet());
    result.removeAll(visited);
    return result;
  }

  private static <T> void unreachable(T current, Map<T, List<T>> graph, Set<T> visited) {
    if (visited.contains(current)) return;
    visited.add(current);

    List<T> neighbors = graph.get(current);
    if (neighbors != null) {
      for (T neighbor : neighbors) {
        unreachable(neighbor, graph, visited);
      }
    }
  }
}
