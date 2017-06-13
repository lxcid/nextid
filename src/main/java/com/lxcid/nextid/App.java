package com.lxcid.nextid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.ParseException;

public class App {
    public static void main(String[] args) {
        Options options = new Options();

        Option startOpt = OptionBuilder.withArgName("ID")
                                       .hasArg()
                                       .withDescription("ID to start with (default to 1)")
                                       .create("start");
        options.addOption(startOpt);
        
        Option totalOpt = OptionBuilder.withArgName("total")
                                       .hasArg()
                                       .withDescription("Total possible IDs (default to 100)")
                                       .create("total");
        options.addOption(totalOpt);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("nextid", options);
            
            System.exit(1);
            return;
        }
        
        int start = Integer.parseInt(cmd.getOptionValue("start", "1"));
        int total = Integer.parseInt(cmd.getOptionValue("total", "100"));
        List<String> argList = cmd.getArgList();

        if (argList.size() == 0) {
            System.out.print(start);
        } else {
            List<Integer> usedIDs = Arrays.stream(argList.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
            HashSet<Integer> possibleIDs = new HashSet<Integer>(total);
            for (int i = start; i < start + total; i++) {
                possibleIDs.add(i);
            }
            possibleIDs.removeAll(usedIDs);
            List<Integer> availableIDs = new ArrayList<Integer>(possibleIDs);
            Collections.sort(availableIDs);
            Integer nextID = availableIDs.get(0);
            System.out.print(nextID);
        }
    }
}
