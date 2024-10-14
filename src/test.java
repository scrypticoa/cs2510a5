//import javalib.funworld.*;
import java.awt.*;

interface ILoColor {
  ILoColor insert(Color color);
  
  ILoColor remove();
  
  ILoColor clone();
  
  ILoColor reverse();
  ILoColor doReverse(ILoColor result);
  
  Result compare(ILoColor other);
  Result comparePass(ILoColor other, Result result);
  Result doCompare(ILoColor other, Result result, Color color);
}

class MtLoColor implements ILoColor {
  
  public ILoColor insert(Color color) {
    return new ConsLoColor(color, new MtLoColor());
  }
  
  public ILoColor remove() {
    return new MtLoColor();
  }
  
  public ILoColor clone() {
    return new MtLoColor();
  }
  
  public ILoColor reverse() {
    return new MtLoColor();
  }
  
  public ILoColor doReverse(ILoColor result) {
    return result;
  }
  
  public Result compare(ILoColor other) {
    return new Result(0, 0);
    // error
  }
  
  public Result comparePass(ILoColor other, Result result) {
    return result;
  }
  
  public Result doCompare(ILoColor other, Result result, Color color) {
    return new Result(0, 0);
    // error
  }
}

class ConsLoColor implements ILoColor {
  Color first;
  ILoColor rest;
  
  public ConsLoColor(Color first, ILoColor rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public ILoColor insert(Color color) {
    return new ConsLoColor(color, this.clone());
  }
  
  public ILoColor remove() {
    return this.rest;
  }
  
  public ILoColor clone() {
    return new ConsLoColor(this.first, this.rest.clone());
  }
  
  public ILoColor reverse() {
    return this.doReverse(new MtLoColor());
  }
  
  public ILoColor doReverse(ILoColor result) {
    return rest.doReverse(new ConsLoColor(first, result));
  }
  
  public Result compare(ILoColor other) {
    return this.comparePass(other, new Result(0, 0));
  }
  
  public Result comparePass(ILoColor other, Result result) {
    return other.doCompare(this.rest, result, this.first);
  }
  
  public Result doCompare(ILoColor other, Result result, Color color) {
    return other.comparePass(this.rest, result);
  }
}

class Result{
  int exact;
  ILoInt answer;
  ILoInt guess;
  
  Result(int exact,  ILoInt answer, ILoInt guess){
    this.exact = exact;
    this.answer = answer;
    this.guess = guess;
  }
}

interface ILoInt{
  //add 1 to the int at position pos
  ILoInt addAtPos(int pos);
  
  //finds sum of list
  int sum();
  
  //takes in another list and compares each int 
  ILoInt mins(ILoInt list);
  // helper to get min at same pos in 2 lists
  int minsCompare(int otherFirst);
  // helper that gets the rest for the next comparison
  ILoInt minsRHelp(ILoInt list);
}

class MtLoInt implements ILoInt{
  public ILoInt addAtPos(int pos) {
    return new MtLoInt();
  }

  public int sum() {
    return 0;
  }
  
  public ILoInt mins(ILoInt list) {
    return new MtLoInt();
  }

  public int minsCompare(int otherFirst) {
    return otherFirst;
  }

  public ILoInt minsRHelp(ILoInt list) {
    return new MtLoInt();
  }
}

class ConsLoInt implements ILoInt{
  int first;
  ILoInt rest;
  
  ConsLoInt(int first, ILoInt rest){
    this.first = first;
    this.rest = rest;
  }
  
  ConsLoInt(int x){
    first = 0;
    if(x > 1) {
      rest =  new ConsLoInt(x-1);
    } else {
      rest = new MtLoInt();
    }
  }

  public ILoInt addAtPos(int pos) {
    if(pos == 0){
      return new ConsLoInt(this.first + 1, this.rest);
    }
    return new ConsLoInt(this.first, this.rest.addAtPos(pos-1));
  }

  public int sum() {
    return first + rest.sum();
  }
  
  public ILoInt mins(ILoInt list) {
    return new ConsLoInt(list.minsCompare(this.first), list.minsRHelp(rest));
  }

  public int minsCompare(int otherFirst) {
    return Math.min(this.first, otherFirst);
  }

  public ILoInt minsRHelp(ILoInt list) {
    return list.mins(this.rest);
  }
}







