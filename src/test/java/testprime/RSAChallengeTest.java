package testprime;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigInteger;

/**
 * This class tests some value at https://en.wikipedia.org/wiki/RSA_numbers
 * */
public class RSAChallengeTest {

  @Test
  public void testRSA100() {
    BigInteger bi = new BigInteger(
        "1522605027922533360535618378132637429718068114961380688657908494580122963258952897654000350692006139");
    doTest(bi);
  }


  @Test
  public void testRSA110() {
    BigInteger bi = new BigInteger(
        "35794234179725868774991807832568455403003778024228226193532908190484670252364677411513516111204504060317568667");
    doTest(bi);
  }



  @Test
  public void testRSA500() {
    BigInteger bi = new BigInteger(
        "18971941337486266563305347433172025272371835919534283031845811230624504588707687605943212347625766427494554764419515427586743205659317254669946604982419730160103812521528540068803151640161162396312837062979326593940508107758169447860417214110246410380402787011098086642148000255604546876251377453934182215494821277335671735153472656328448001134940926442438440198910908603252678814785060113207728717281994244511323201949222955423789860663107489107472242561739680319169243814676235712934292299974411361");
    doTest(bi);
  }



  @Test
  public void testRSA617() {
    BigInteger bi = new BigInteger(
        "22701801293785014193580405120204586741061235962766583907094021879215171483119139894870133091111044901683400949483846818299518041763507948922590774925466088171879259465921026597046700449819899096862039460017743094473811056991294128542891880855362707407670722593737772666973440977361243336397308051763091506836310795312607239520365290032105848839507981452307299417185715796297454995023505316040919859193718023307414880446217922800831766040938656344571034778553457121080530736394535923932651866030515041060966437313323672831539323500067937107541955437362433248361242525945868802353916766181532375855504886901432221349733");

    doTest(bi);
  }

  @Test
  public void testRSA2048() {
    BigInteger bi = new BigInteger(
        "25195908475657893494027183240048398571429282126204032027777137836043662020707595556264018525880784406918290641249515082189298559149176184502808489120072844992687392807287776735971418347270261896375014971824691165077613379859095700097330459748808428401797429100642458691817195118746121515172654632282216869987549182422433637259085141865462043576798423387184774447920739934236584823824281198163815010674810451660377306056201619676256133844143603833904414952634432190114657544454178424020924616515723350778707749817125772467962926386356373289912154831438167899885040445364023527381951378636564391212010397122822120720357");
    doTest(bi);
  }

  void doTest(BigInteger target) {
    assertFalse(target.isProbablePrime(100));
  }
}
