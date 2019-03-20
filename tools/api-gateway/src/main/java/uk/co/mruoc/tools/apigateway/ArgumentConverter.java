package uk.co.mruoc.tools.apigateway;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentConverter {

    private static final String REGION_ARGUMENT = "region";
    private static final String NAME_ARGUMENT = "name";
    private static final String STAGE_ARGUMENT = "stage";

    private static final String DEFAULT_REGION = "eu-west-1";

    private final CommandLineParser parser;
    private final Options options;

    public ArgumentConverter() {
        this(new DefaultParser());
    }

    public ArgumentConverter(final CommandLineParser parser) {
        this(parser, buildOptions());
    }

    public ArgumentConverter(final CommandLineParser parser, final Options options) {
        this.parser = parser;
        this.options = options;
    }

    public FindApiRequest toFindApiRequest(final String[] args) {
        final CommandLine commandLine = parse(args);

        final String region = commandLine.getOptionValue(REGION_ARGUMENT, DEFAULT_REGION);
        final String name = commandLine.getOptionValue(NAME_ARGUMENT);
        final String stage = commandLine.getOptionValue(STAGE_ARGUMENT);

        return FindApiRequest.builder()
                .region(region)
                .name(name)
                .stage(stage)
                .build();
    }

    private CommandLine parse(final String[] args) {
        try {
            return parser.parse(options, args);
        } catch (final ParseException e) {
            throw new InvalidArgumentsException(e);
        }
    }

    private static Options buildOptions() {
        final Option regionOption = Option.builder("r")
                .longOpt(REGION_ARGUMENT)
                .hasArg(true)
                .desc("AWS region")
                .required(false)
                .build();

        final Option nameOption = Option.builder("n")
                .longOpt(NAME_ARGUMENT)
                .hasArg(true)
                .desc("Name of API to find")
                .required(true)
                .build();

        final Option stageOption = Option.builder("s")
                .longOpt(STAGE_ARGUMENT)
                .hasArg(true)
                .desc("Stage of API to find")
                .required(true)
                .build();

        final Options options = new Options();
        options.addOption(regionOption);
        options.addOption(nameOption);
        options.addOption(stageOption);
        return options;
    }

    public static class InvalidArgumentsException extends RuntimeException {

        public InvalidArgumentsException(final Throwable cause) {
            super(cause);
        }

    }

}
