package org.cytoscape.engnet.model.businessobjects.exceptions;

public class AnalysisErrorException extends RuntimeException {
    
    /**
     * This field holds the exception ex if the
     * ClassNotFoundException(String s, Throwable ex) constructor was
     * used to instantiate the object
     * @serial
     * @since 1.2
     */
    private final Throwable ex;

    private static final String DefaultMessage = "No results have been obtained with the provided input parameters. Please verify the entered values and ensure they are suitable for performing the search.";

    /**
     * Constructs a <code>DataBaseException</code> with the
     * default detail message and optional exception that was
     * raised while loading the class.
     *
     * @param ex the exception that was raised while loading the class
     * @since 1.2
     */
    public AnalysisErrorException(Throwable ex) {
        this(DefaultMessage, null);
    }

    /**
     * Constructs a <code>DataBaseException</code> with the
     * specified detail message and optional exception that was
     * raised while loading the class.
     *
     * @param s the detail message
     * @since 1.2
     */
    public AnalysisErrorException(String s) {
        this(s, null);
    }
    
    /**
     * Constructs a <code>DataBaseException</code> with the
     * specified detail message and optional exception that was
     * raised while loading the class.
     *
     * @param s the detail message
     * @param ex the exception that was raised while loading the class
     * @since 1.2
     */
    public AnalysisErrorException(String s, Throwable ex) {
        super(s, null); // Disallow initCause
        this.ex = ex;
    }

    /**
     * Returns the exception that was raised if an error occurred while
     * attempting to load the class. Otherwise, returns <tt>null</tt>.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * @return the <code>Exception</code> that was raised while loading a class
     * @since 1.2
     */
    public Throwable getException() {
        return ex;
    }

    /**
     * Returns the cause of this exception (the exception that was raised
     * if an error occurred while attempting to load the class; otherwise
     * <tt>null</tt>).
     *
     * @return  the cause of this exception.
     * @since   1.4
     */
    @Override
    public Throwable getCause() {
        return ex;
    }
}
