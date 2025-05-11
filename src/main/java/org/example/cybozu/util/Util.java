package org.example.cybozu.util;

import org.example.locale.LdLocale;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;


public class Util {
    /**
     * normalize probabilities and check convergence by the maximum probability
     * @return maximum of probabilities
     */
    public static double normalizeProb(double[] prob) {
        double maxp = 0, sump = 0;
        for(int i=0;i<prob.length;++i) sump += prob[i];
        for(int i=0;i<prob.length;++i) {
            double p = prob[i] / sump;
            if (maxp < p) maxp = p;
            prob[i] = p;
        }
        return maxp;
    }

    public static double[] makeInternalPrioMap(@NotNull Map<LdLocale, Double> langWeightingMap,
                                                @NotNull List<LdLocale> langlist) {
        assert !langWeightingMap.isEmpty();
        double[] priorMap = new double[langlist.size()];
        double sump = 0;
        for (int i=0;i<priorMap.length;++i) {
            LdLocale lang = langlist.get(i);
            if (langWeightingMap.containsKey(lang)) {
                double p = langWeightingMap.get(lang);
                assert p>=0 : "Prior probability must be non-negative!";
                priorMap[i] = p;
                sump += p;
            }
        }
        assert sump > 0 : "Sum must be greater than zero!";
        for (int i=0;i<priorMap.length;++i) priorMap[i] /= sump;
        return priorMap;
    }

}
