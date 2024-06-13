package edu.tm1.krzysztof.jurkowski.i20.projekt2.pathfind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class Node {
	private int x, y, distance;
}
