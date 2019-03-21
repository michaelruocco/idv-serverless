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

        return FindApiRequest.builder()
                .region(commandLine.getOptionValue(REGION_ARGUMENT, DEFAULT_REGION))
                .name(commandLine.getOptionValue(NAME_ARGUMENT))
                .stage(commandLine.getOptionValue(STAGE_ARGUMENT))
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
        final Options options = new Options();
        options.addOption(buildRegionOption());
        options.addOption(buildNameOption());
        options.addOption(buildStageOption());
        return options;
    }

    private static Option buildRegionOption() {
        return Option.builder("r")
                .longOpt(REGION_ARGUMENT)
                .hasArg(true)
                .desc("AWS region")
                .required(false)
                .build();
    }

    private static Option buildNameOption() {
        return Option.builder("n")
                .longOpt(NAME_ARGUMENT)
                .hasArg(true)
                .desc("Name of API to find")
                .required(true)
                .build();
    }

    private static Option buildStageOption() {
        return Option.builder("s")
                .longOpt(STAGE_ARGUMENT)
                .hasArg(true)
                .desc("Stage of API to find")
                .required(true)
                .build();
    }

    public static class InvalidArgumentsException extends RuntimeException {

        public InvalidArgumentsException(final Throwable cause) {
            super(cause);
        }

    }

}
