//import javalib.funworld.*;
import java.awt.*;

interface ILoColor {

}

class MtLoColor implements ILoColor {
  
}

class ConsLoColor implements ILoColor {
  Color first;
  ILoColor rest;
  
  public ConsLoColor(Color first, ILoColor rest) {
    this.first = first;
    this.rest = rest;
  }
}

class Game {
  Result compareResult;
  
  int numColors;
  
  // constructor settings
  ILoColor gameColors;
  int sequenceLength;
  int attemptCount;
  boolean duplicatesAllowed;
  
  public Game(ILoColor gameColors, int sequenceLength,
      int attemptCount, boolean duplicatesAllowed) {
    this.gameColors = gameColors;
    this.sequenceLength = sequenceLength;
    this.attemptCount = attemptCount;
    this.duplicatesAllowed = duplicatesAllowed;
  }
  
  public Result genBlankResult() {
    return new Result(
        0,
        new ConsLoInt(numColors),
        new ConsLoInt(numColors));
  }
}

class Result {
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
  ILoInt addAtPos(int pos);
  
  ILoInt insert(int num);
  
  ILoInt remove();
  
  ILoInt clone();
  
  Result compare(ILoInt other, Game game);
  Result comparePass(ILoInt other, Game game, Result result);
  Result doCompare(ILoInt other, Game game, Result result, int num);
}

class MtLoInt implements ILoInt{
  public ILoInt addAtPos(int pos) {
    return new MtLoInt();
  }
  
  public ILoInt insert(int num) {
    return new ConsLoInt(num, new MtLoInt());
  }
  
  public ILoInt remove() {
    return new MtLoInt();
  }
  
  public ILoInt clone() {
    return new MtLoInt();
  }
  
  public Result compare(ILoInt other, Game game) {
    return game.genBlankResult();
    // error
  }
  
  public Result comparePass(ILoInt other, Game game, Result result) {
    return result;
  }
  
  public Result doCompare(ILoInt other, Game game, Result result, int num) {
    return game.genBlankResult();
    // error
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
  
  public ILoInt insert(int num) {
    return new ConsLoInt(num, this.clone());
  }
  
  public ILoInt remove() {
    return this.rest;
  }
  
  public ILoInt clone() {
    return new ConsLoInt(this.first, this.rest.clone());
  }
  
  public Result compare(ILoInt other, Game game) {
    Result result = game.genBlankResult();
    return this.comparePass(other, game, result);
  }
  
  public Result comparePass(ILoInt other, Game game, Result result) {
    return other.doCompare(this.rest, game, result, this.first);
  }
  
  public Result doCompare(ILoInt other, Game game, Result result, int num) {
    return other.comparePass(this.rest, game, result);
  }
}







