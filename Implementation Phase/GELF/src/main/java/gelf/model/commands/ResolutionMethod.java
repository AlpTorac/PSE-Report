package gelf.model.commands;

/**
 * Enum keeping track of how a naming conflict has been decided to be resolved
 * by the user, so that the model can resolve it
 * @author Xhulio Pernoca
 */
public enum ResolutionMethod {
    KEEPRIGHT,
    KEEPLEFT,
    RENAMERIGHT,
    RENAMELEFT,
    CANCEL;
}
