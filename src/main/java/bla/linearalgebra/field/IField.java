package bla.linearalgebra.field;

import bla.linearalgebra.IRing;

public interface IField<T> extends IRing<T> {

	T inv(T element);

	T div(T left, T right);
}
