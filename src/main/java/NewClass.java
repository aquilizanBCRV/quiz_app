/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yuzuki
 */
class SuperClass {

    protected int x;

    public SuperClass(int x) {
        this.x = x;
    }
}

class SubClass extends SuperClass {

    public SubClass(int x) {
        super(x + 1);
    }
}

public class NewClass {

    public static void main(String args[]) {
//        int[]
        SubClass obj = new SubClass(5);
        System.out.println(obj.x);
    }
}
