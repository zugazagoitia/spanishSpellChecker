package com.zugazagoitia.spanishSpellChecker;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.spellchecker.SpellCheckerManager;
import com.intellij.spellchecker.dictionary.ProjectDictionary;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class SpanishDict implements StartupActivity {
    private static final Logger LOG = Logger.getInstance("#SpanishDict");
    @NonNls
    private static final String DICT_NAME = "Diccionario Español";
    @NonNls
    private static final String DICT_URL = "/dict/spanish.dic";
    private static final Charset cs = StandardCharsets.UTF_8;


    public SpanishDict() {
    }

    @Override
    public void runActivity(@NotNull Project project) {
        SpellCheckerManager manager = SpellCheckerManager.getInstance(project);
        InputStream is = SpanishDict.class.getResourceAsStream(DICT_URL);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            List<String> allLines = new LinkedList<>();
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                allLines.add(strLine);
            }
            ProjectDictionary d = new com.intellij.spellchecker.dictionary.ProjectDictionary();
            d.setActiveName(DICT_NAME);
            d.addToDictionary(allLines);
            manager.getSpellChecker().addDictionary(d);
        } catch (IOException e) {
            LOG.warn(e);
        }

    }


}
