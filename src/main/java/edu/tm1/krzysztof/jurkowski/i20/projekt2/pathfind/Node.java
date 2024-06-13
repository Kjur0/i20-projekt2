package edu.tm1.krzysztof.jurkowski.i20.projekt2.pathfind;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
public class Node implements Comparable<Node>{
	private int x,y,cost;

	@Override
	public int compareTo(Node other) {
		return Integer.compare(this.cost, other.cost);
	}
}
