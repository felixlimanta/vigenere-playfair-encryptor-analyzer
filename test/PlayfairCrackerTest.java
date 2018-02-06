package com.felixlimanta.VigenerePlayfair;

import org.junit.Test;

public class PlayfairCrackerTest {

  private static final String cipherText =
      "SUNLSQQKEIGLSLUOGSPBPYLKCTVLKAKFDIQBNSLEVLDUIUTCYAQTCPQDRCIUKWIBDUVK"
      + "UDLABKDMOUDCQSGOLKDAKTSKVNAORYEGLEKMNVKSIPLMDCAOAIQUKMNVKSIADCQWQEEAYLBCAWSMZXLPNVKIPSDK"
      + "PGPRLDUQLHQTAMKFZDVICUTIQKTVOKOACKUDSYKWKLQDBCLALMCOPFKOIHSPOQTLVAKSSEQTBCKEQUEFCQFLTQMC"
      + "TCOHLKCTDDQKTVTCLKNSVADIOAKLPYBCSKRKQTBCAKUQRYMSDQUOPKAOPLCVQKMATCOHLKPSLVELLVESLKKIYGVT"
      + "LGQUYQLMCOPFSMBCLSSPVIEMKFTIOSTFSDBCSKEWBCQIEMKFLPCKEKSXAKVTPYLEQMCTKSNLMQTCGMCVPKDMELBC"
      + "OAWLMITUSDCTDIBHQTKOCQFNLPGOLPTEBCOUGECNAODWOALYLPCOPGLPQTDIABPYVTQKRESLUOGSTLBIOKPKTIUE"
      + "TUKLGKAODQUALPDSQTOAEGQTHASPDMSMCVITLPCEIVLKDSNASGLEBCMOTQKSCEPSKSEMNVKVLKPVTCMSQKCPCUIP"
      + "XGQIUQYRLPUVSPOQFXDQOADIKQSUFVDIWQKAYPCVQKLPFWOKWSEQBALKCIQIPKUOLENLFQQUEISLWLHOGSCVQKQT"
      + "CQFNLPCOMIKITRLYEGOAMSAMKFZDAOSKLMDCQTCRTCDMEUTCOKTVOAGKUQDLLVTVQKMRBCQUVCOSTCUITCTIDUWQ"
      + "AEMMKFKRSQCVGSANPYCVLEPROAWLQKBCKOOUSDCVQKQDKOIHSPOQPLAQLDQUSELVLCQUIKDMIABCAWPYLEIUGYVT"
      + "KOKIDMPYBCSPQTCQFNLPFDCUEFOAKFQKCTPSIPTIDUWQMQPKQSBCAWPYLESWLKWAPYEIEFBCAWGMWSALLVMCUEKE"
      + "QUBOBCLPBCSOYGLWAOTVKQBCAODIQBPIOSEMDCQSCEVLIDOQSDQKUEVIPMDTRYQIKFVTALLVEIGLTQABKOUSLKNV"
      + "KISGEGLSVIKAQGQUSMTFLMKFBCAOSOTVQKAXQKLEPSKFPYCOWMCKLEKVCUDCVTEGSUTQTCAOKOQTBCLDQTMAWAPY"
      + "QDCUHCTIDUWQEQWQZDSPPYSKDMSMQTGLICQUTCGMKBOAWSPKMSAOVILPTCOHLKCTGQUESGCUSWQTCPDMTOKFQDMI"
      + "FVDSLVTCGMNCQTLFVTCMBIOKPKEMNVPSQTQVVCDMOAPYSKGULWSKCUPFTCGMBCSYAKIEVCVSLAAPQLDLICKOBCLO"
      + "RMSQPVQDLQQTLFVTCMDCQWQETCGMPSOAMPELPKQTMSAOVILPTIDUWQAECQIACTCQFNLPFCMEHZQSPSTIDUWQAEVA"
      + "KSSBGMBCMEIWAOCVSLTCOAPFBCLKKOKOIHSPOQPLALRIVCVPCUESLMTLLMQAVGCUEFDUWQOKHOGSCVQKDQYPNLAA"
      + "DCQSGOLKHQKFQTRPNLAEDCQWQELVEIBCAWGSPTSKVNAOELIYSKDUELVOBCATSDRSALHOGSCVQKMAKLWLCESGVFLE"
      + "VLKIDMMABCLPOELALNKMQTCAAOSVDSELDQYPNLSFCUEFOATQPLNLAEDCQWQEEMKFLPSPOQPLSFIUYQVIQMZBBCSP"
      + "QTTVTCTCGMCTDCQWQELCIDIMQKBCKEDUALDCQSGOSPDMIPGMFLPLQYUQYEGSOKSUIVSGCUSWLSLKKPYNRKQTOQTL"
      + "DUQMCEDCQWQEMTLPDUSLEWOPQYIASDQKCKLMDCAOTVFQQTAMKFGMWSCVSLKIEWOPKIDABCAOVOBCBCSOIGQELNLM"
      + "HRLEHOUOPFBCLEDCQWQEGEAPFVDIOGQTCPIKDMEUTCOADSPKGEPSKBUQSVOKHKVPDMWKDFBCLMWSYPDAKSSGCUPF"
      + "EQEGQDOITQQUCUHCTIDUWQSNLMDCAOTVFQQTAMKFVTMPQIBCMADXGMCVCVTVPKDMQBVCKOTCSPLESNGMKSPLNMLE"
      + "KSSGDMIAKIHZQSGKUQDLLVTVQKMAKLKVQUTEVLGMOAOCLMEMKFKMTCGYSGCVDSTCCUTVEMTQABQUPSPKDIKFVCOQ"
      + "PKWKBFLPIVPFKSSGCUPFRIMAVCVGVFCQPLNLAXNAPADITLCVBIKSEMTVFPQCVILPKBPYKICQKFVSOCRMAFUQVKGM"
      + "KFALPYTQPFQTCQFLTQMCTIDUWQAESWAOVGAMLVESIMKTLDQELNWEKOPZPACQCNLWLKSUBQKSOADSLVMTOAEPEIDC"
      + "QSGOLKGSTLKAKFCVSLQICTOCKWPKDMOUWEPLICVSVCOSLVTIDUWQEFPAEMOAMAEIMCQUKSUOFQQTAMKFBCLPQTBC"
      + "MDQBVCKOTCSPLELPGKUOSMYATIDUWQAEDKWLBKDMDUSLEWOPEQEGKIMSQTUSZBEITCOHLKCTGMWSCVSLDQIPDMOU"
      + "WEBFUKZNOSEMKFGQOSKFEVDMTLDCQWQEVTIWAOLWSKDMCOQLDLOATFSOATUQKQQBDSCOPLSLUOGSZDAOLMPLCVQK"
      + "MAKRNLEQDLALSLPKPYVADCQWQEGYQTIVGPAEDQQBCKGMBIBKQDQUPVKAIGLKKIHQLVTIDUWQEFVTPIVICTDCSYAO"
      + "KOIHKOQULAUSQTSPKOOADSLVTVKQOGAOKIGQLWLSQUSKUSLDQUDIOASGEGLSVIEMKFQKSOFQQIOAALSLPKPYVADC"
      + "QWQELTQEQKDAYGUQSVOKAISDLEVLVNRENLAFYPOKETWNUCLMYGKWNAKWCNLKPYHRQTCPGMKTSDMRELBCMOTQKSCE"
      + "PSKSTVESIHUHRCYFAOEUOACOWSIALMKFEUOAQBLORMSQCTGMOGAOKIOASGFZCUHCAOLORMSQPVKOIHSPOQPTIBSW"
      + "LKQCBCVTEKOAPIOMVCEWQMFKIKLWNASRKXGZQTCQFNLPCEBIOKPKEALMQPKMNVKSUOPKDMOUTIDUWQAEBCLPQTEU"
      + "TCIKDMOANASGLEQTBCKEQUEFNLKQTRTQQKALKNOCLVEALWAKAMKSNVCTUELMATSDCTQDBCLMSLUOGSTLDCQWQEAV"
      + "TCLSPYLKIKDMANHQSQTQABUQSVPFKOIHSPOQFXRMSQPVLPOKRIVCXGOUTCKOIHSPOQTQPKDMQBVCKOTCSPLESQTC"
      + "QWUEIHCFLDQTKOPZKSTCQTRPKFWFSPSRGHSKVNQTCPDMGEPSIMQTHGOARILKCTPSKFPYCOWMCKLECTIDVFRBOSMI"
      + "BKQWQKSKCUOGQDMALNSYSRAMNMSKUSLSKIKSSGCBGMKVQSLNMAQKAKOAWSPKTCOHLKCTGQUEZLEPQTCPDMSMOPTC"
      + "DMVADUQMFELNELDIKFMSAOVILPKOIHSPOQPBAMDSTCQWUEIHCGSRBCQTSMOPSKNASGSDDUVIKECUDCGPOAPKMCSY"
      + "MKSDMRELQDLWGPKFKOSGVFLEVLKIDCPYKSIADCQWQEEABCVTEKEPLKTCDMBRNMVITIDUWQAEPIIHBIOABCSPVTHQ"
      + "KFMSLKPSSNUELPVAIHVFLEVLHQALHOGSCVQKAVTCLSELAEKEDMOAQYQGQTMALNZAQULEBIOKPKTVPLSLUOGSGMWS"
      + "KQSVDSQUKQPBUQDLLVTVQKMAKSSGDMIAQTBCSOIGAMLVAIQWUETCWOPABCLPSWAOGQLOOMPKVNIUKQWSUQCNLMDC"
      + "AOAIQTBCQIEMKFEIGLSLUOGSTLDCQWQEMIKFAOSUKLTCDMWLPKOULWKOIHSPOQTLVAKSSLLNZDSPEWQMHKSKDMYE"
      + "XGQUYEOGBCLKBCMOTQKSCEPSKSTILPKTSDUELDQTAMKFHZNVBCAOSOEMEQCVCILPALPYPLKUIBZDSPEWOPEMKFZD"
      + "SPOWLEPKNYKRKLLGLQQUGENMLMKFESPYCTKSKYQIEFKMKIKSUAQTSPAKQEVIOSEMKFGYSGCVDSTCGMKBOAWSPKMS"
      + "AOVILPKSSGDMIAGQUEKSSGCUPFZDSPSMNVKSIAEUTCKKIKDMOUTCOHLKCTOAOKTVFPQTHDIUYQLKKBQBVCVSLMFV"
      + "DIQBPSTUSQPVDQIADCQWQEEMKFMPQEVTCUPFQBLFSDLEVLPYVAPSKFPYCOWMCKLECTEWQMGOLSIWSWMPRCHZOGLK"
      + "GQUETIDUWQAELPFELMWSKSSGDMIAVOBCEUOACVSLKIDQHIEIPQLMNLALPYTQPFFLQL";

  @Test
  public void crack() throws Exception {
    PlayfairCracker crack = new PlayfairCracker(cipherText);

    double score;
    double maxScore = Integer.MIN_VALUE;
    for (int i = 0; i < 10000; ++i) {
      System.out.printf("Iteration: %d\n", i);
      score = crack.crack();
      System.out.printf("Current score: %f\n", score);
      if (score > maxScore) {
        maxScore = score;
        System.out.printf("Best score so far: %f, on iteration %d\n", maxScore, i);
        System.out.printf("Key:\n");
        crack.printTable();
        System.out.print("Plaintext:\n");
        System.out.println(crack.getPlainText());
        System.out.println();
      }
    }
  }
}