package com.dutertry.adventofcode;

import java.math.BigInteger;

public class Rational implements Comparable<Rational> {
    public static Rational ZERO = new Rational(BigInteger.ZERO, BigInteger.ONE);
    public static Rational ONE = new Rational(BigInteger.ONE, BigInteger.ONE);

    private BigInteger num;   // the numerator
    private BigInteger den;   // the denominator

    // create and initialize a new Rational object
    public Rational(BigInteger numerator, BigInteger denominator) {

        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("denominator is zero");
        }

        // reduce fraction
        BigInteger g = gcd(numerator, denominator);
        num = numerator.divide(g);
        den = denominator.divide(g);

        // needed only for negative numbers
        if (den.compareTo(BigInteger.ZERO) < 0) { den = den.negate(); num = num.negate(); }
    }

    // return the numerator and denominator of (this)
    public BigInteger numerator()   { return num; }
    public BigInteger denominator() { return den; }

    // return double precision representation of (this)
    public double toDouble() {
        return (double) num.doubleValue() / den.doubleValue();
    }

    // return string representation of (this)
    public String toString() {
        if (den.equals(BigInteger.ONE)) return num + "";
        else          return num + "/" + den;
    }

    // return { -1, 0, +1 } if a < b, a = b, or a > b
    public int compareTo(Rational b) {
        Rational a = this;
        BigInteger lhs = a.num.multiply(b.den);
        BigInteger rhs = a.den.multiply(b.num);
        if (lhs.compareTo(rhs) < 0) return -1;
        if (lhs.compareTo(rhs) > 0) return +1;
        return 0;
    }

    // is this Rational object equal to y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Rational b = (Rational) y;
        return compareTo(b) == 0;
    }

    // hashCode consistent with equals() and compareTo()
    // (better to hash the numerator and denominator and combine)
    public int hashCode() {
        return this.toString().hashCode();
    }


    // create and return a new rational (r.num + s.num) / (r.den + s.den)
    public static Rational mediant(Rational r, Rational s) {
        return new Rational(r.num.add(s.num), r.den.add(s.den));
    }

    // return gcd(|m|, |n|)
    private static BigInteger gcd(BigInteger m, BigInteger n) {
        if (m.compareTo(BigInteger.ZERO) < 0) m = m.negate();
        if (n.compareTo(BigInteger.ZERO) < 0) n = n.negate();
        if (n.equals(BigInteger.ZERO)) return m;
        else return gcd(n, m.remainder(n));
    }

    // return lcm(|m|, |n|)
    private static BigInteger lcm(BigInteger m, BigInteger n) {
        if (m.compareTo(BigInteger.ZERO) < 0) m = m.negate();
        if (n.compareTo(BigInteger.ZERO) < 0) n = n.negate();
        return m.multiply(n.divide(gcd(m, n)));    // parentheses important to avoid overflow
    }

    // return a * b, staving off overflow as much as possible by cross-cancellation
    public Rational times(Rational b) {
        Rational a = this;

        // reduce p1/q2 and p2/q1, then multiply, where a = p1/q1 and b = p2/q2
        Rational c = new Rational(a.num, b.den);
        Rational d = new Rational(b.num, a.den);
        return new Rational(c.num.multiply(d.num), c.den.multiply(d.den));
    }


    // return a + b, staving off overflow
    public Rational plus(Rational b) {
        Rational a = this;

        // special cases
        if (a.compareTo(ZERO) == 0) return b;
        if (b.compareTo(ZERO) == 0) return a;

        // Find gcd of numerators and denominators
        BigInteger f = gcd(a.num, b.num);
        BigInteger g = gcd(a.den, b.den);

        // add cross-product terms for numerator
        Rational s = new Rational(a.num.divide(f).multiply(b.den.divide(g)).add(b.num.divide(f).multiply(a.den.divide(g))),
                                  lcm(a.den, b.den));

        // multiply back in
        s.num = s.num.multiply(f);
        return s;
    }

    // return -a
    public Rational negate() {
        return new Rational(num.negate(), den);
    }

    // return |a|
    public Rational abs() {
        if (num.compareTo(BigInteger.ZERO) >= 0) return this;
        else return negate();
    }

    // return a - b
    public Rational minus(Rational b) {
        Rational a = this;
        return a.plus(b.negate());
    }


    public Rational reciprocal() { return new Rational(den, num);  }

    // return a / b
    public Rational divides(Rational b) {
        Rational a = this;
        return a.times(b.reciprocal());
    }
}
