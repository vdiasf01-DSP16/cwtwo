package classes;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import controllers.ICodeGenerator;
import controllers.IColourLoader;
import controllers.IFlowController;
import controllers.IPegGenerator;
import factories.CodeGeneratorFactory;
import factories.ColourLoaderFactory;
import factories.FlowControllerFactory;
import factories.PegGeneratorFactory;
import factories.StartTextFactory;
import factories.TextBeforeGuessFactory;
import views.IStartText;
import views.ITextBeforeGuess;

public class GameImpl extends GameAbstractImpl {

	@Inject
	private PegGeneratorFactory pegGeneratorFactory;

	@Inject
	public GameImpl(@Named("easy") boolean easy) {
		super(easy);
	}

	@Override
	public void runGames() {

		// Start text
		StartTextFactory startTextFactory = new StartTextFactory();
		IStartText startText = startTextFactory.factoryMethod();
		startText.show();

		// Load colours from file
		ColourLoaderFactory colourLoaderFactory = new ColourLoaderFactory();
		IColourLoader colourLoader = colourLoaderFactory.factoryMethod();

		// Create peg generator with loaded colours
		IPegGenerator pegGenerator = pegGeneratorFactory.create(colourLoader.getColours());

		
		CodeGeneratorFactory codeGeneratorFactory = new CodeGeneratorFactory();
		ICodeGenerator codeGenerator = codeGeneratorFactory.factoryMethod(pegGenerator);
		codeGenerator.generateNewCode();

		FlowControllerFactory flowControllerFactory = new FlowControllerFactory();
		IFlowController flowController = flowControllerFactory.factoryMethod();

		TextBeforeGuessFactory textBeforeGuessFactory = new TextBeforeGuessFactory();
		boolean keepGuessing = false;
		do {
			ITextBeforeGuess textBeforeGuess = textBeforeGuessFactory.factoryMethod();
			// textBeforeGuess.show(showCode, codeGenerator.getCodeString());
			// TODO add guess play to guess history

			keepGuessing = flowController.isGameFinished();
		} while (keepGuessing);
	}

}
