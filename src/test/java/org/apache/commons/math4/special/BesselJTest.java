/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math4.special;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.special.BesselJ;
import org.junit.Assert;
import org.junit.Test;

/**
 * @version $Id$
 */
public class BesselJTest {

    /**
     * Reference data for the {@link BesselJ#value(double, double)} function. This data
     * was generated with the following <a href="http://www.r-project.org/">R</a> script.
     *
     * <pre>
     * smallxs = 10 ** (-(seq(0,8,.5)))
     * medxs = seq(1,20)
     * near.eight = 8 + seq(-.5,.5,.1)
     * largexs = c(10,30,100,300,1000)
     * xs = unique(sort(c(smallxs, medxs, near.eight,largexs)))
     *          
     * for (n in c(0:15, 30, 100)) {
     * for (x in xs) {
     * val = format( besselJ(x,n), digits=20 )
     * s = paste("{", n, ",", x, ",", val, "},")
     * print(s)
     * }
     * }
     * </pre>
     */
    private static final double[][] BESSEL_J_REF = {
        { 0 , 1e-08 , 1 },
        { 0 , 3.16227766016838e-08 , 0.99999999999999977796 },
        { 0 , 1e-07 , 0.99999999999999744649 },
        { 0 , 3.16227766016838e-07 , 0.99999999999997501998 },
        { 0 , 1e-06 , 0.99999999999974997777 },
        { 0 , 3.16227766016838e-06 , 0.99999999999749999979 },
        { 0 , 1e-05 , 0.99999999997499999793 },
        { 0 , 3.16227766016838e-05 , 0.99999999974999997931 },
        { 0 , 1e-04 , 0.99999999750000001519 },
        { 0 , 0.000316227766016838 , 0.99999997500000015194 },
        { 0 , 0.001 , 0.9999997500000156192 },
        { 0 , 0.00316227766016838 , 0.99999750000156251151 },
        { 0 , 0.01 , 0.99997500015624951608 },
        { 0 , 0.0316227766016838 , 0.9997500156245660019 },
        { 0 , 0.1 , 0.99750156206604001508 },
        { 0 , 0.316227766016838 , 0.97515581664971295872 },
        { 0 , 1 , 0.76519768655796649437 },
        { 0 , 2 , 0.22389077914123567403 },
        { 0 , 3 , -0.26005195490193344643 },
        { 0 , 4 , -0.39714980986384734729 },
        { 0 , 5 , -0.177596771314338292 },
        { 0 , 6 , 0.15064525725099692233 },
        { 0 , 7 , 0.3000792705195556298 },
        { 0 , 7.5 , 0.26633965788037838873 },
        { 0 , 7.6 , 0.25160183384997636402 },
        { 0 , 7.7 , 0.23455913958646437689 },
        { 0 , 7.8 , 0.21540780774626291927 },
        { 0 , 7.9 , 0.19436184484127824734 },
        { 0 , 8 , 0.17165080713755390129 },
        { 0 , 8.1 , 0.14751745404437766052 },
        { 0 , 8.2 , 0.12221530178413773926 },
        { 0 , 8.3 , 0.09600610089501022959 },
        { 0 , 8.4 , 0.069157261656985186127 },
        { 0 , 8.5 , 0.041939251842934503756 },
        { 0 , 9 , -0.090333611182876139001 },
        { 0 , 10 , -0.24593576445134832098 },
        { 0 , 11 , -0.17119030040719609986 },
        { 0 , 12 , 0.0476893107968335353 },
        { 0 , 13 , 0.20692610237706782206 },
        { 0 , 14 , 0.17107347611045864433 },
        { 0 , 15 , -0.014224472826780772475 },
        { 0 , 16 , -0.17489907398362919411 },
        { 0 , 17 , -0.16985425215118354902 },
        { 0 , 18 , -0.013355805721984111492 },
        { 0 , 19 , 0.14662943965965119508 },
        { 0 , 20 , 0.16702466434058313438 },
        { 0 , 30 , -0.086367983581040239094 },
        { 0 , 100 , 0.019985850304223118368 },
        { 0 , 300 , -0.033298554876305661021 },
        { 0 , 1000 , 0.024786686152420175921 },
        { 1 , 1e-08 , 5.0000000000000001046e-09 },
        { 1 , 3.16227766016838e-08 , 1.5811388300841892647e-08 },
        { 1 , 1e-07 , 4.999999999999993818e-08 },
        { 1 , 3.16227766016838e-07 , 1.5811388300841697432e-07 },
        { 1 , 1e-06 , 4.9999999999993750869e-07 },
        { 1 , 3.16227766016838e-06 , 1.5811388300822132559e-06 },
        { 1 , 1e-05 , 4.9999999999375003889e-06 },
        { 1 , 3.16227766016838e-05 , 1.5811388298865475016e-05 },
        { 1 , 1e-04 , 4.9999999937500002646e-05 },
        { 1 , 0.000316227766016838 , 0.00015811388103199544184 },
        { 1 , 0.001 , 0.00049999993750000253697 },
        { 1 , 0.00316227766016838 , 0.0015811368536614756226 },
        { 1 , 0.01 , 0.0049999375002604158624 },
        { 1 , 0.0316227766016838 , 0.015809411959653556917 },
        { 1 , 0.1 , 0.049937526036241998428 },
        { 1 , 0.316227766016838 , 0.15614567743386048582 },
        { 1 , 1 , 0.44005058574493355339 },
        { 1 , 2 , 0.57672480775687340326 },
        { 1 , 3 , 0.33905895852593642692 },
        { 1 , 4 , -0.066043328023549133232 },
        { 1 , 5 , -0.32757913759146523036 },
        { 1 , 6 , -0.27668385812756562947 },
        { 1 , 7 , -0.0046828234823458325664 },
        { 1 , 7.5 , 0.13524842757970551022 },
        { 1 , 7.6 , 0.15921376839635667522 },
        { 1 , 7.7 , 0.18131271532458800855 },
        { 1 , 7.8 , 0.20135687275589611578 },
        { 1 , 7.9 , 0.21917939992175122788 },
        { 1 , 8 , 0.23463634685391462908 },
        { 1 , 8.1 , 0.2476077669815928417 },
        { 1 , 8.2 , 0.25799859764868082745 },
        { 1 , 8.3 , 0.26573930204186430037 },
        { 1 , 8.4 , 0.27078626827683538458 },
        { 1 , 8.5 , 0.27312196367405372488 },
        { 1 , 9 , 0.24531178657332528004 },
        { 1 , 10 , 0.043472746168861438332 },
        { 1 , 11 , -0.17678529895672151495 },
        { 1 , 12 , -0.2234471044906276016 },
        { 1 , 13 , -0.070318052121778371055 },
        { 1 , 14 , 0.13337515469879324126 },
        { 1 , 15 , 0.20510403861352277666 },
        { 1 , 16 , 0.090397175661304188243 },
        { 1 , 17 , -0.097668492757780639435 },
        { 1 , 18 , -0.18799488548806958521 },
        { 1 , 19 , -0.10570143114240927729 },
        { 1 , 20 , 0.066833124175850036619 },
        { 1 , 30 , -0.1187510626166229516 },
        { 1 , 100 , -0.077145352014112156258 },
        { 1 , 300 , -0.031887431377499955709 },
        { 1 , 1000 , 0.0047283119070895248195 },
        { 2 , 1e-08 , 1.2499999999999999739e-17 },
        { 2 , 3.16227766016838e-08 , 1.2499999999999998506e-16 },
        { 2 , 1e-07 , 1.2499999999999988152e-15 },
        { 2 , 3.16227766016838e-07 , 1.2499999999999894672e-14 },
        { 2 , 1e-06 , 1.249999999999895719e-13 },
        { 2 , 3.16227766016838e-06 , 1.2499999999989582746e-12 },
        { 2 , 1e-05 , 1.2499999999895835475e-11 },
        { 2 , 3.16227766016838e-05 , 1.2499999998958334818e-10 },
        { 2 , 1e-04 , 1.2499999989583335487e-09 },
        { 2 , 0.000316227766016838 , 1.2499999895833333493e-08 },
        { 2 , 0.001 , 1.2499998958333367811e-07 },
        { 2 , 0.00316227766016838 , 1.2499989583336589057e-06 },
        { 2 , 0.01 , 1.2499895833658854395e-05 },
        { 2 , 0.0316227766016838 , 0.00012498958365884872863 },
        { 2 , 0.1 , 0.0012489586587999190141 },
        { 2 , 0.316227766016838 , 0.012396158312196680837 },
        { 2 , 1 , 0.11490348493190047363 },
        { 2 , 2 , 0.35283402861563772923 },
        { 2 , 3 , 0.48609126058589108288 },
        { 2 , 4 , 0.36412814585207281537 },
        { 2 , 5 , 0.046565116277752213736 },
        { 2 , 6 , -0.24287320996018546548 },
        { 2 , 7 , -0.3014172200859401296 },
        { 2 , 7.5 , -0.23027341052579025638 },
        { 2 , 7.6 , -0.20970347374567196996 },
        { 2 , 7.7 , -0.18746492781384410664 },
        { 2 , 7.8 , -0.16377784037295622932 },
        { 2 , 7.9 , -0.13887338916488553564 },
        { 2 , 8 , -0.11299172042407523708 },
        { 2 , 8.1 , -0.086379733802009056598 },
        { 2 , 8.2 , -0.059288814552752158726 },
        { 2 , 8.3 , -0.031972534137934507936 },
        { 2 , 8.4 , -0.0046843406386910518141 },
        { 2 , 8.5 , 0.022324739609784025052 },
        { 2 , 9 , 0.14484734153250397592 },
        { 2 , 10 , 0.25463031368512062391 },
        { 2 , 11 , 0.13904751877870125121 },
        { 2 , 12 , -0.084930494878604809172 },
        { 2 , 13 , -0.21774426424195678087 },
        { 2 , 14 , -0.15201988258205964555 },
        { 2 , 15 , 0.041571677975250471981 },
        { 2 , 16 , 0.18619872094129222284 },
        { 2 , 17 , 0.15836384123850347216 },
        { 2 , 18 , -0.0075325148878013998069 },
        { 2 , 19 , -0.15775590609569428713 },
        { 2 , 20 , -0.16034135192299814321 },
        { 2 , 30 , 0.07845124607326538213 },
        { 2 , 100 , -0.021528757344505360799 },
        { 2 , 300 , 0.033085972000455661501 },
        { 2 , 1000 , -0.024777229528605997089 },
        { 3 , 1e-08 , 2.0833333333333334614e-26 },
        { 3 , 3.16227766016838e-08 , 6.5880784586841223417e-25 },
        { 3 , 1e-07 , 2.0833333333333317693e-23 },
        { 3 , 3.16227766016838e-07 , 6.5880784586840819929e-22 },
        { 3 , 1e-06 , 2.0833333333332027799e-20 },
        { 3 , 3.16227766016838e-06 , 6.5880784586800051603e-19 },
        { 3 , 1e-05 , 2.0833333333203129762e-17 },
        { 3 , 3.16227766016838e-05 , 6.5880784582723696076e-16 },
        { 3 , 1e-04 , 2.083333332031250315e-14 },
        { 3 , 0.000316227766016838 , 6.5880784175086335665e-13 },
        { 3 , 0.001 , 2.0833332031250032117e-11 },
        { 3 , 0.00316227766016838 , 6.5880743411361163135e-10 },
        { 3 , 0.01 , 2.083320312532551971e-08 },
        { 3 , 0.0316227766016838 , 6.5876667140741846331e-07 },
        { 3 , 0.1 , 2.0820315754756265453e-05 },
        { 3 , 0.316227766016838 , 0.00065470057642003857534 },
        { 3 , 1 , 0.019563353982668403586 },
        { 3 , 2 , 0.1289432494744020552 },
        { 3 , 3 , 0.30906272225525166508 },
        { 3 , 4 , 0.43017147387562193472 },
        { 3 , 5 , 0.36483123061366695694 },
        { 3 , 6 , 0.11476838482077529602 },
        { 3 , 7 , -0.16755558799533423753 },
        { 3 , 7.5 , -0.25806091319346030621 },
        { 3 , 7.6 , -0.26958401773618401176 },
        { 3 , 7.7 , -0.27869709340970183487 },
        { 3 , 7.8 , -0.28534550884459158882 },
        { 3 , 7.9 , -0.2894950400052375139 },
        { 3 , 8 , -0.29113220706595221987 },
        { 3 , 8.1 , -0.29026442564925164502 },
        { 3 , 8.2 , -0.28691997060124291297 },
        { 3 , 8.3 , -0.28114775222882071315 },
        { 3 , 8.4 , -0.27301690667621203445 },
        { 3 , 8.5 , -0.26261620385768480457 },
        { 3 , 9 , -0.1809351903366568648 },
        { 3 , 10 , 0.058379379305186815396 },
        { 3 , 11 , 0.22734803305806741691 },
        { 3 , 12 , 0.19513693953109267909 },
        { 3 , 13 , 0.0033198169704070513292 },
        { 3 , 14 , -0.17680940686509599713 },
        { 3 , 15 , -0.19401825782012263599 },
        { 3 , 16 , -0.043847495425981139472 },
        { 3 , 17 , 0.13493057304919323092 },
        { 3 , 18 , 0.18632099329078039007 },
        { 3 , 19 , 0.072489661438052577225 },
        { 3 , 20 , -0.098901394560449676363 },
        { 3 , 30 , 0.12921122875972501642 },
        { 3 , 100 , 0.076284201720331942798 },
        { 3 , 300 , 0.032328577670839367397 },
        { 3 , 1000 , -0.0048274208252039483777 },
        { 4 , 1e-08 , 2.6041666666666666342e-35 },
        { 4 , 3.16227766016838e-08 , 2.6041666666666659714e-33 },
        { 4 , 1e-07 , 2.6041666666666649861e-31 },
        { 4 , 3.16227766016838e-07 , 2.6041666666666531276e-29 },
        { 4 , 1e-06 , 2.6041666666665358894e-27 },
        { 4 , 3.16227766016838e-06 , 2.6041666666653639536e-25 },
        { 4 , 1e-05 , 2.6041666666536465525e-23 },
        { 4 , 3.16227766016838e-05 , 2.604166666536458817e-21 },
        { 4 , 1e-04 , 2.6041666653645840559e-19 },
        { 4 , 0.000316227766016838 , 2.6041666536458338462e-17 },
        { 4 , 0.001 , 2.6041665364583368871e-15 },
        { 4 , 0.00316227766016838 , 2.604165364583604802e-13 },
        { 4 , 0.01 , 2.6041536458604605458e-11 },
        { 4 , 0.0316227766016838 , 2.6040364610459735901e-09 },
        { 4 , 0.1 , 2.6028648545684040871e-07 },
        { 4 , 0.316227766016838 , 2.5911729278009268374e-05 },
        { 4 , 1 , 0.002476638964109955255 },
        { 4 , 2 , 0.033995719807568429427 },
        { 4 , 3 , 0.13203418392461221953 },
        { 4 , 4 , 0.28112906496136008672 },
        { 4 , 5 , 0.39123236045864817623 },
        { 4 , 6 , 0.35764159478096080313 },
        { 4 , 7 , 0.15779814466136793394 },
        { 4 , 7.5 , 0.02382467997102201071 },
        { 4 , 7.6 , -0.0031260139407891210546 },
        { 4 , 7.7 , -0.029701638479430046702 },
        { 4 , 7.8 , -0.055718704892114230554 },
        { 4 , 7.9 , -0.080996261472003727722 },
        { 4 , 8 , -0.10535743487538892782 },
        { 4 , 8.1 , -0.12863095186410331006 },
        { 4 , 8.2 , -0.15065262735059631316 },
        { 4 , 8.3 , -0.17126680482265874139 },
        { 4 , 8.4 , -0.19032773555860327264 },
        { 4 , 8.5 , -0.2077008835093262229 },
        { 4 , 9 , -0.26547080175694187654 },
        { 4 , 10 , -0.21960268610200855965 },
        { 4 , 11 , -0.015039500747028132846 },
        { 4 , 12 , 0.18249896464415113484 },
        { 4 , 13 , 0.21927648745906774819 },
        { 4 , 14 , 0.076244422497018474183 },
        { 4 , 15 , -0.11917898110329952499 },
        { 4 , 16 , -0.20264153172603513453 },
        { 4 , 17 , -0.11074128604467056713 },
        { 4 , 18 , 0.069639512651394869236 },
        { 4 , 19 , 0.18064737812876355272 },
        { 4 , 20 , 0.13067093355486322781 },
        { 4 , 30 , -0.05260900032132037607 },
        { 4 , 100 , 0.026105809447725277644 },
        { 4 , 300 , -0.032439400447038871378 },
        { 4 , 1000 , 0.024748265003654772859 },
        { 5 , 1e-08 , 2.6041666666666666817e-44 },
        { 5 , 3.16227766016838e-08 , 8.2350980733551520153e-42 },
        { 5 , 1e-07 , 2.6041666666666648818e-39 },
        { 5 , 3.16227766016838e-07 , 8.2350980733551185479e-37 },
        { 5 , 1e-06 , 2.6041666666665576923e-34 },
        { 5 , 3.16227766016838e-06 , 8.2350980733517218878e-32 },
        { 5 , 1e-05 , 2.6041666666558171668e-29 },
        { 5 , 3.16227766016838e-05 , 8.2350980730120282499e-27 },
        { 5 , 1e-04 , 2.6041666655815978351e-24 },
        { 5 , 0.000316227766016838 , 8.2350980390422474771e-22 },
        { 5 , 0.001 , 2.6041665581597245947e-19 },
        { 5 , 0.00316227766016838 , 8.2350946420649040367e-17 },
        { 5 , 0.01 , 2.6041558159915982186e-14 },
        { 5 , 0.0316227766016838 , 8.2347549503960048977e-12 },
        { 5 , 0.1 , 2.6030817909644421178e-09 },
        { 5 , 0.316227766016838 , 8.2008463739855235578e-07 },
        { 5 , 1 , 0.00024975773021123438876 },
        { 5 , 2 , 0.0070396297558716850601 },
        { 5 , 3 , 0.043028434877047584683 },
        { 5 , 4 , 0.13208665604709826646 },
        { 5 , 5 , 0.26114054612017006951 },
        { 5 , 6 , 0.36208707488717239986 },
        { 5 , 7 , 0.34789632475118331678 },
        { 5 , 7.5 , 0.28347390516255044357 },
        { 5 , 7.6 , 0.26629347674587972028 },
        { 5 , 7.7 , 0.2478382482362680439 },
        { 5 , 7.8 , 0.22819811921165392143 },
        { 5 , 7.9 , 0.20747350940067682545 },
        { 5 , 8 , 0.18577477219056331981 },
        { 5 , 8.1 , 0.16322151022791506203 },
        { 5 , 8.2 , 0.13994179757627084326 },
        { 5 , 8.3 , 0.11607131384553516507 },
        { 5 , 8.4 , 0.091752396620399440108 },
        { 5 , 8.5 , 0.067133019378318919967 },
        { 5 , 9 , -0.055038855669513706004 },
        { 5 , 10 , -0.23406152818679362704 },
        { 5 , 11 , -0.23828585178317879256 },
        { 5 , 12 , -0.073470963101658584571 },
        { 5 , 13 , 0.13161955992748081146 },
        { 5 , 14 , 0.22037764829196368477 },
        { 5 , 15 , 0.1304561345650295523 },
        { 5 , 16 , -0.057473270437036434732 },
        { 5 , 17 , -0.18704411942315585238 },
        { 5 , 18 , -0.15537009877904933708 },
        { 5 , 19 , 0.0035723925109004857348 },
        { 5 , 20 , 0.15116976798239498136 },
        { 5 , 30 , -0.14324029551207712041 },
        { 5 , 100 , -0.074195736964513925304 },
        { 5 , 300 , -0.03319362834942707341 },
        { 5 , 1000 , 0.0050254069452331864842 },
        { 6 , 1e-08 , 2.170138888888889163e-53 },
        { 6 , 3.16227766016838e-08 , 2.1701388888888880947e-50 },
        { 6 , 1e-07 , 2.1701388888888875174e-47 },
        { 6 , 3.16227766016838e-07 , 2.170138888888880604e-44 },
        { 6 , 1e-06 , 2.1701388888888106952e-41 },
        { 6 , 3.16227766016838e-06 , 2.1701388888881133808e-38 },
        { 6 , 1e-05 , 2.1701388888811393588e-35 },
        { 6 , 3.16227766016838e-05 , 2.1701388888113848269e-32 },
        { 6 , 1e-04 , 2.1701388881138396044e-29 },
        { 6 , 0.000316227766016838 , 2.1701388811383932341e-26 },
        { 6 , 0.001 , 2.1701388113839301844e-23 },
        { 6 , 0.00316227766016838 , 2.1701381138394068717e-20 },
        { 6 , 0.01 , 2.1701311384049674283e-17 },
        { 6 , 0.0316227766016838 , 2.1700613851395740978e-14 },
        { 6 , 0.1 , 2.1693639603760032489e-11 },
        { 6 , 0.316227766016838 , 2.1624004918010960028e-08 },
        { 6 , 1 , 2.0938338002389272967e-05 },
        { 6 , 2 , 0.0012024289717899932714 },
        { 6 , 3 , 0.011393932332213070266 },
        { 6 , 4 , 0.049087575156385579445 },
        { 6 , 5 , 0.13104873178169201831 },
        { 6 , 6 , 0.24583686336432652997 },
        { 6 , 7 , 0.33919660498317966146 },
        { 6 , 7.5 , 0.35414052691237862813 },
        { 6 , 7.6 , 0.35351216755378872536 },
        { 6 , 7.7 , 0.35156949333172621275 },
        { 6 , 7.8 , 0.3482803961891063893 },
        { 6 , 7.9 , 0.34362095691589844559 },
        { 6 , 8 , 0.33757590011359311921 },
        { 6 , 8.1 , 0.33013898918251699532 },
        { 6 , 8.2 , 0.32131335610214611931 },
        { 6 , 8.3 , 0.3111117612630625584 },
        { 6 , 8.4 , 0.29955677915431688785 },
        { 6 , 8.5 , 0.28668090630734854862 },
        { 6 , 9 , 0.20431651767970440692 },
        { 6 , 10 , -0.014458842084785107282 },
        { 6 , 11 , -0.20158400087404348966 },
        { 6 , 12 , -0.24372476722886662892 },
        { 6 , 13 , -0.1180306721302363665 },
        { 6 , 14 , 0.081168183425812737153 },
        { 6 , 15 , 0.20614973747998591169 },
        { 6 , 16 , 0.16672073770288736716 },
        { 6 , 17 , 0.00071533344281418307069 },
        { 6 , 18 , -0.15595623419531115528 },
        { 6 , 19 , -0.17876717154407903432 },
        { 6 , 20 , -0.055086049563665764883 },
        { 6 , 30 , 0.0048622351506280026001 },
        { 6 , 100 , -0.033525383144176669481 },
        { 6 , 300 , 0.031332946168724638836 },
        { 6 , 1000 , -0.024698010934202440508 },
        { 7 , 1e-08 , 1.5500992063492066701e-62 },
        { 7 , 3.16227766016838e-08 , 4.9018440912828279875e-59 },
        { 7 , 1e-07 , 1.5500992063492053031e-55 },
        { 7 , 3.16227766016838e-07 , 4.9018440912828133382e-52 },
        { 7 , 1e-06 , 1.55009920634915736e-48 },
        { 7 , 3.16227766016838e-06 , 4.9018440912812964979e-45 },
        { 7 , 1e-05 , 1.550099206344363137e-41 },
        { 7 , 3.16227766016838e-05 , 4.9018440911296494971e-38 },
        { 7 , 1e-04 , 1.5500992058648010339e-34 },
        { 7 , 0.000316227766016838 , 4.9018440759645687969e-31 },
        { 7 , 0.001 , 1.5500991579086071003e-27 },
        { 7 , 0.00316227766016838 , 4.9018425594567649302e-24 },
        { 7 , 0.01 , 1.550094362295914728e-20 },
        { 7 , 0.0316227766016838 , 4.9016909107824929132e-17 },
        { 7 , 0.1 , 1.5496148676202282287e-13 },
        { 7 , 0.316227766016838 , 4.8865470861431505644e-10 },
        { 7 , 1 , 1.5023258174368078499e-06 },
        { 7 , 2 , 0.00017494407486827416175 },
        { 7 , 3 , 0.0025472944518046929108 },
        { 7 , 4 , 0.015176069422058449318 },
        { 7 , 5 , 0.053376410155890716136 },
        { 7 , 6 , 0.12958665184148068783 },
        { 7 , 7 , 0.23358356950569605925 },
        { 7 , 7.5 , 0.28315093789725531703 },
        { 7 , 7.6 , 0.29188362991799726709 },
        { 7 , 7.7 , 0.30006226085213638655 },
        { 7 , 7.8 , 0.30761787492543296585 },
        { 7 , 7.9 , 0.31448237452220684229 },
        { 7 , 8 , 0.32058907797982633125 },
        { 7 , 8.1 , 0.3258732885609990082 },
        { 7 , 8.2 , 0.33027286989028453723 },
        { 7 , 8.3 , 0.33372882292033839713 },
        { 7 , 8.4 , 0.33618585931433897507 },
        { 7 , 8.5 , 0.33759296599676130723 },
        { 7 , 9 , 0.32746087924245292911 },
        { 7 , 10 , 0.21671091768505151842 },
        { 7 , 11 , 0.018376032647858614455 },
        { 7 , 12 , -0.17025380412720803047 },
        { 7 , 13 , -0.24057094958616048741 },
        { 7 , 14 , -0.15080491964126707671 },
        { 7 , 15 , 0.034463655418959161791 },
        { 7 , 16 , 0.18251382371420196704 },
        { 7 , 17 , 0.1875490606769070201 },
        { 7 , 18 , 0.051399275982175231248 },
        { 7 , 19 , -0.11647797453873988405 },
        { 7 , 20 , -0.18422139772059445417 },
        { 7 , 30 , 0.1451851895723283159 },
        { 7 , 100 , 0.070172690987212724134 },
        { 7 , 300 , 0.034446946196176060628 },
        { 7 , 1000 , -0.0053217830764436153956 },
        { 8 , 1e-08 , 9.6881200396825412359e-72 },
        { 8 , 3.16227766016838e-08 , 9.6881200396825359082e-68 },
        { 8 , 1e-07 , 9.6881200396825335915e-64 },
        { 8 , 3.16227766016838e-07 , 9.6881200396825091166e-60 },
        { 8 , 1e-06 , 9.6881200396822669073e-56 },
        { 8 , 3.16227766016838e-06 , 9.6881200396798449492e-52 },
        { 8 , 1e-05 , 9.6881200396556345156e-48 },
        { 8 , 3.16227766016838e-05 , 9.6881200394134308774e-44 },
        { 8 , 1e-04 , 9.6881200369913995322e-40 },
        { 8 , 0.000316227766016838 , 9.688120012771098157e-36 },
        { 8 , 0.001 , 9.6881197705681010442e-32 },
        { 8 , 0.00316227766016838 , 9.688117348538421731e-28 },
        { 8 , 0.01 , 9.6880931282716245736e-24 },
        { 8 , 0.0316227766016838 , 9.6878509286008909493e-20 },
        { 8 , 0.1 , 9.6854292315946525669e-16 },
        { 8 , 0.316227766016838 , 9.6612422089625085973e-12 },
        { 8 , 1 , 9.4223441726045005392e-08 },
        { 8 , 2 , 2.2179552287925904881e-05 },
        { 8 , 3 , 0.00049344177620883479096 },
        { 8 , 4 , 0.0040286678208190035769 },
        { 8 , 5 , 0.018405216654802002835 },
        { 8 , 6 , 0.056531990932461785582 },
        { 8 , 7 , 0.12797053402821254031 },
        { 8 , 7.5 , 0.17440789049583127479 },
        { 8 , 7.6 , 0.18416820334778524759 },
        { 8 , 7.7 , 0.19399825367215817185 },
        { 8 , 7.8 , 0.20385425111295268907 },
        { 8 , 7.9 , 0.21368958021206302389 },
        { 8 , 8 , 0.22345498635110294661 },
        { 8 , 8.1 , 0.23309879351550599758 },
        { 8 , 8.2 , 0.24256715346663235144 },
        { 8 , 8.3 , 0.25180432559052018382 },
        { 8 , 8.4 , 0.26075298636958132992 },
        { 8 , 8.5 , 0.26935456709908189854 },
        { 8 , 9 , 0.30506707225300011554 },
        { 8 , 10 , 0.31785412684385727644 },
        { 8 , 11 , 0.22497167878949989039 },
        { 8 , 12 , 0.045095329080457241533 },
        { 8 , 13 , -0.14104573511639803551 },
        { 8 , 14 , -0.23197310306707982774 },
        { 8 , 15 , -0.17398365908895732646 },
        { 8 , 16 , -0.0070211419529606520704 },
        { 8 , 17 , 0.1537368341734622057 },
        { 8 , 18 , 0.19593344884811411677 },
        { 8 , 19 , 0.092941295568165452345 },
        { 8 , 20 , -0.073868928840750344711 },
        { 8 , 30 , 0.062890853316458550371 },
        { 8 , 100 , 0.043349559882386451415 },
        { 8 , 300 , -0.029725422012903089664 },
        { 8 , 1000 , 0.02462350597113223058 } ,
        { 9 , 1e-08 , 5.382288910934745386e-81 },
        { 9 , 3.16227766016838e-08 , 1.702029198362092975e-76 },
        { 9 , 1e-07 , 5.3822889109347404393e-72 },
        { 9 , 3.16227766016838e-07 , 1.7020291983620889989e-67 },
        { 9 , 1e-06 , 5.3822889109346077433e-63 },
        { 9 , 3.16227766016838e-06 , 1.7020291983616675345e-58 },
        { 9 , 1e-05 , 5.3822889109212923968e-54 },
        { 9 , 3.16227766016838e-05 , 1.7020291983195439884e-49 },
        { 9 , 1e-04 , 5.3822889095891748719e-45 },
        { 9 , 0.000316227766016838 , 1.7020291941070210695e-40 },
        { 9 , 0.001 , 5.382288776377523226e-36 },
        { 9 , 0.00316227766016838 , 1.7020287728548427703e-31 },
        { 9 , 0.01 , 5.3822754552277587118e-27 },
        { 9 , 0.0316227766016838 , 1.7019866481156611902e-22 },
        { 9 , 0.1 , 5.3809434916023306372e-18 },
        { 9 , 0.316227766016838 , 1.6977789573201714453e-13 },
        { 9 , 1 , 5.2492501799118757129e-09 },
        { 9 , 2 , 2.492343435133064173e-06 },
        { 9 , 3 , 8.4395021309091773631e-05 },
        { 9 , 4 , 0.00093860186121756401367 },
        { 9 , 5 , 0.005520283139475687037 },
        { 9 , 6 , 0.021165323978417364265 },
        { 9 , 7 , 0.058920508273075426764 },
        { 9 , 7.5 , 0.088919228493851462658 },
        { 9 , 7.6 , 0.095838903445761125521 },
        { 9 , 7.7 , 0.10305099353156887965 },
        { 9 , 7.8 , 0.11054469146011103309 },
        { 9 , 7.9 , 0.11830664869209804591 },
        { 9 , 8 , 0.12632089472237958971 },
        { 9 , 8.1 , 0.13456877270419806414 },
        { 9 , 8.2 , 0.14302889297143717151 },
        { 9 , 8.3 , 0.15167710592885710885 },
        { 9 , 8.4 , 0.16048649567533976312 },
        { 9 , 8.5 , 0.16942739560151048872 },
        { 9 , 9 , 0.2148805825406584491 },
        { 9 , 10 , 0.29185568526512006837 },
        { 9 , 11 , 0.30885550013686852155 },
        { 9 , 12 , 0.23038090956781773211 },
        { 9 , 13 , 0.066976198673670619965 },
        { 9 , 14 , -0.11430719814968128001 },
        { 9 , 15 , -0.22004622511384699934 },
        { 9 , 16 , -0.18953496566716260263 },
        { 9 , 17 , -0.042855569690119083015 },
        { 9 , 18 , 0.12276378966059287023 },
        { 9 , 19 , 0.19474432870140553908 },
        { 9 , 20 , 0.12512625464799415065 },
        { 9 , 30 , -0.11164340113688375755 },
        { 9 , 100 , -0.063236761406030891908 },
        { 9 , 300 , -0.036032302036864222172 },
        { 9 , 1000 , 0.0057157591719817308837 },
        { 10 , 1e-08 , 2.6911444554673727331e-90 },
        { 10 , 3.16227766016838e-08 , 2.6911444554673710334e-85 },
        { 10 , 1e-07 , 2.6911444554673703522e-80 },
        { 10 , 3.16227766016838e-07 , 2.6911444554673646193e-75 },
        { 10 , 1e-06 , 2.6911444554673096152e-70 },
        { 10 , 3.16227766016838e-06 , 2.6911444554667591352e-65 },
        { 10 , 1e-05 , 2.6911444554612582946e-60 },
        { 10 , 3.16227766016838e-05 , 2.6911444554062112799e-55 },
        { 10 , 1e-04 , 2.6911444548557502587e-50 },
        { 10 , 0.000316227766016838 , 2.691144449351135505e-45 },
        { 10 , 0.001 , 2.6911443943049990395e-40 },
        { 10 , 0.00316227766016838 , 2.6911438438436965201e-35 },
        { 10 , 0.01 , 2.6911383392363445476e-30 },
        { 10 , 0.0316227766016838 , 2.691083293730485964e-25 },
        { 10 , 0.1 , 2.6905328954342172306e-20 },
        { 10 , 0.316227766016838 , 2.6850345850670040022e-15 },
        { 10 , 1 , 2.630615123687452921e-10 },
        { 10 , 2 , 2.5153862827167368199e-07 },
        { 10 , 3 , 1.292835164571588302e-05 },
        { 10 , 4 , 0.00019504055466003448463 },
        { 10 , 5 , 0.0014678026473104743583 },
        { 10 , 6 , 0.0069639810027903158857 },
        { 10 , 7 , 0.023539344388267140901 },
        { 10 , 7.5 , 0.038998257889412211996 },
        { 10 , 7.6 , 0.042818673234280582585 },
        { 10 , 7.7 , 0.04690017276527555512 },
        { 10 , 7.8 , 0.051248883025765065713 },
        { 10 , 7.9 , 0.055869872504109699407 },
        { 10 , 8 , 0.060767026774251164944 },
        { 10 , 8.1 , 0.065942923604934144954 },
        { 10 , 8.2 , 0.071398709153595626975 },
        { 10 , 8.3 , 0.077133976423868738648 },
        { 10 , 8.4 , 0.083146647220432454151 },
        { 10 , 8.5 , 0.089432858880587384753 },
        { 10 , 9 , 0.12469409282831672714 },
        { 10 , 10 , 0.20748610663335886883 },
        { 10 , 11 , 0.28042823052537591 },
        { 10 , 12 , 0.300476035271269315 },
        { 10 , 13 , 0.23378201020301889179 },
        { 10 , 14 , 0.085006705446061009424 },
        { 10 , 15 , -0.09007181104765905888 },
        { 10 , 16 , -0.20620569442259728543 },
        { 10 , 17 , -0.19911331972770593413 },
        { 10 , 18 , -0.073169659187521246535 },
        { 10 , 19 , 0.091553331622639774756 },
        { 10 , 20 , 0.18648255802394508862 },
        { 10 , 30 , -0.129876893998588816 },
        { 10 , 100 , -0.054732176935472012791 },
        { 10 , 300 , 0.027563483890691235778 },
        { 10 , 1000 , -0.02452062230603655954 },
        { 30 , 1e-08 , 3.511074584737334481e-282 },
        { 30 , 3.16227766016838e-08 , 3.5110745847373276748e-267 },
        { 30 , 1e-07 , 3.5110745847373271436e-252 },
        { 30 , 3.16227766016838e-07 , 3.5110745847373244839e-237 },
        { 30 , 1e-06 , 3.5110745847372989351e-222 },
        { 30 , 3.16227766016838e-06 , 3.511074584737044636e-207 },
        { 30 , 1e-05 , 3.5110745847345094386e-192 },
        { 30 , 3.16227766016838e-05 , 3.5110745847090235522e-177 },
        { 30 , 1e-04 , 3.5110745844541855471e-162 },
        { 30 , 0.000316227766016838 , 3.5110745819058229075e-147 },
        { 30 , 0.001 , 3.5110745564222159037e-132 },
        { 30 , 0.00316227766016838 , 3.5110743015861690319e-117 },
        { 30 , 0.01 , 3.5110717532266786188e-102 },
        { 30 , 0.0316227766016838 , 3.5110462697303107185e-87 },
        { 30 , 0.1 , 3.5107914446214635799e-72 },
        { 30 , 0.316227766016838 , 3.5082441787554764315e-57 },
        { 30 , 1 , 3.4828697942514824077e-42 },
        { 30 , 2 , 3.6502562664740960186e-33 },
        { 30 , 3 , 6.7223399381463293316e-28 },
        { 30 , 4 , 3.5570357020361055268e-24 },
        { 30 , 5 , 2.6711772782507989195e-21 },
        { 30 , 6 , 5.7984683652785706951e-19 },
        { 30 , 7 , 5.3172607940100176027e-17 },
        { 30 , 7.5 , 3.9705139492720914996e-16 },
        { 30 , 7.6 , 5.8351206236969734897e-16 },
        { 30 , 7.7 , 8.5295046954365007979e-16 },
        { 30 , 7.8 , 1.240300099862031423e-15 },
        { 30 , 7.9 , 1.7943809060373146352e-15 },
        { 30 , 8 , 2.5830997825663086363e-15 },
        { 30 , 8.1 , 3.7004810818946501642e-15 },
        { 30 , 8.2 , 5.2761304350589830578e-15 },
        { 30 , 8.3 , 7.4879207291538333461e-15 },
        { 30 , 8.4 , 1.057892772982890842e-14 },
        { 30 , 8.5 , 1.4879948521285087748e-14 },
        { 30 , 9 , 7.6921564693354977569e-14 },
        { 30 , 10 , 1.5510960782574666161e-12 },
        { 30 , 11 , 2.2735383676316185421e-11 },
        { 30 , 12 , 2.5522590430344176732e-10 },
        { 30 , 13 , 2.2828783239868354402e-09 },
        { 30 , 14 , 1.6775399533577877891e-08 },
        { 30 , 15 , 1.0374710201078721135e-07 },
        { 30 , 16 , 5.5052386643076382366e-07 },
        { 30 , 17 , 2.5460065118711982301e-06 },
        { 30 , 18 , 1.0393652487465728599e-05 },
        { 30 , 19 , 3.7849142225173515583e-05 },
        { 30 , 20 , 0.00012401536360354329497 },
        { 30 , 30 , 0.14393585001030734238 },
        { 30 , 100 , 0.081460129581172213697 },
        { 30 , 300 , -0.029514887800373371812 },
        { 30 , 1000 , -0.020271896981075843147 },
        { 100 , 1e-08 , 0 },
        { 100 , 3.16227766016838e-08 , 0 },
        { 100 , 1e-07 , 0 },
        { 100 , 3.16227766016838e-07 , 0 },
        { 100 , 1e-06 , 0 },
        { 100 , 3.16227766016838e-06 , 0 },
        { 100 , 1e-05 , 0 },
        { 100 , 3.16227766016838e-05 , 0 },
        { 100 , 1e-04 , 0 },
        { 100 , 0.000316227766016838 , 0 },
        { 100 , 0.001 , 0 },
        { 100 , 0.00316227766016838 , 0 },
        { 100 , 0.01 , 0 },
        { 100 , 0.0316227766016838 , 0 },
        { 100 , 0.1 , 8.4525165351217888791e-289 },
        { 100 , 0.316227766016838 , 8.4506337559752745816e-239 },
        { 100 , 1 , 8.4318287896267070128e-189 },
        { 100 , 2 , 1.0609531124391718917e-158 },
        { 100 , 3 , 4.260360181132621405e-141 },
        { 100 , 4 , 1.305547836452271925e-128 },
        { 100 , 5 , 6.2677893955418752099e-119 },
        { 100 , 6 , 5.0513258541507019365e-111 },
        { 100 , 7 , 2.4215591572118171706e-104 },
        { 100 , 7.5 , 2.3583800455568589368e-101 },
        { 100 , 7.6 , 8.8352979458474109476e-101 },
        { 100 , 7.7 , 3.253025120751429903e-100 },
        { 100 , 7.8 , 1.1776236102157393805e-99 },
        { 100 , 7.9 , 4.1933885427120016432e-99 },
        { 100 , 8 , 1.4694094093552327336e-98 },
        { 100 , 8.1 , 5.0688862671208964077e-98 },
        { 100 , 8.2 , 1.7220304874625643909e-97 },
        { 100 , 8.3 , 5.7635248300942440709e-97 },
        { 100 , 8.4 , 1.9011188242236321325e-96 },
        { 100 , 8.5 , 6.182346491260611201e-96 },
        { 100 , 9 , 1.8369106342703587456e-93 },
        { 100 , 10 , 6.5973160641553802341e-89 },
        { 100 , 11 , 8.6297901331738815878e-85 },
        { 100 , 12 , 4.8983704457507876536e-81 },
        { 100 , 13 , 1.3781127544328333402e-77 },
        { 100 , 14 , 2.1310751903146119988e-74 },
        { 100 , 15 , 1.9660095611249536378e-71 },
        { 100 , 16 , 1.1559435724349575529e-68 },
        { 100 , 17 , 4.5721265690179434188e-66 },
        { 100 , 18 , 1.2722370655682102766e-63 },
        { 100 , 19 , 2.5856336302772506687e-61 },
        { 100 , 20 , 3.9617550943362506795e-59 },
        { 100 , 30 , 4.5788015281752424119e-42 },
        { 100 , 100 , 0.09636667329586150188 },
        { 100 , 300 , -0.014491227064785699996 },
        { 100 , 1000 , 0.011676135007802557891 },
        { 300 , 1e-08 , 0 },
        { 300 , 3.16227766016838e-08 , 0 },
        { 300 , 1e-07 , 0 },
        { 300 , 3.16227766016838e-07 , 0 },
        { 300 , 1e-06 , 0 },
        { 300 , 3.16227766016838e-06 , 0 },
        { 300 , 1e-05 , 0 },
        { 300 , 3.16227766016838e-05 , 0 },
        { 300 , 1e-04 , 0 },
        { 300 , 0.000316227766016838 , 0 },
        { 300 , 0.001 , 0 },
        { 300 , 0.00316227766016838 , 0 },
        { 300 , 0.01 , 0 },
        { 300 , 0.0316227766016838 , 0 },
        { 300 , 0.1 , 0 },
        { 300 , 0.316227766016838 , 0 },
        { 300 , 1 , 0 },
        { 300 , 2 , 0 },
        { 300 , 3 , 0 },
        { 300 , 4 , 0 },
        { 300 , 5 , 0 },
        { 300 , 6 , 0 },
        { 300 , 7 , 0 },
        { 300 , 7.5 , 0 },
        { 300 , 7.6 , 0 },
        { 300 , 7.7 , 0 },
        { 300 , 7.8 , 0 },
        { 300 , 7.9 , 0 },
        { 300 , 8 , 0 },
        { 300 , 8.1 , 0 },
        { 300 , 8.2 , 0 },
        { 300 , 8.3 , 0 },
        { 300 , 8.4 , 0 },
        { 300 , 8.5 , 0 },
        { 300 , 9 , 0 },
        { 300 , 10 , 0 },
        { 300 , 11 , 0 },
        { 300 , 12 , 0 },
        { 300 , 13 , 0 },
        { 300 , 14 , 0 },
        { 300 , 15 , 0 },
        { 300 , 16 , 0 },
        { 300 , 17 , 0 },
        { 300 , 18 , 0 },
        { 300 , 19 , 0 },
        { 300 , 20 , 0 },
        { 300 , 30 , 1.0388021531643495593e-262 },
        { 300 , 100 , 3.5203666218469330448e-109 },
        { 300 , 300 , 0.066818398128979980544 },
        { 300 , 1000 , 0.00046782803879124944908 }
    };

    @Test
    public void testBesselJ() {
        final double tol = 1e-15;

        for (int i = 0; i < BESSEL_J_REF.length; i++) {
            final double[] data = BESSEL_J_REF[i];
            final double order = data[0];
            final double x = data[1];
            final double expected = data[2];
            final double actual = BesselJ.value(order, x);

            String msg = "" + order + " @ " + x;
            Assert.assertEquals(msg, expected, actual, tol);
        }
    }
    
    @Test(expected=MathIllegalArgumentException.class)
    public void testIAEBadOrder() {
        BesselJ.value(-1, 1);
    }
    
    @Test(expected=MathIllegalArgumentException.class)
    public void testIAEBadArgument() {
        BesselJ.value(1, 100000);
    }
}
