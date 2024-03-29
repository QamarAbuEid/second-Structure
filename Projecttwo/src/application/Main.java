package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			FileChooser fileChooser = new FileChooser();
			Button load = new Button("LOAD");
			Label path = new Label();
			HBox hbox = new HBox(10);
			Button Prev = new Button("Prev");
			Button Next = new Button("Next");
			HBox PN = new HBox(30);
			PN.getChildren().addAll(Prev, Next);
			hbox.getChildren().addAll(path, load);
			Scene scene = new Scene(root, 400, 400);
			TextField textField = new TextField("");
			TextField textField1 = new TextField("");
			TextArea first=new TextArea("");
			TextArea text = new TextArea("");
			TextArea textField2 = new TextArea("");
			TextField textField3 = new TextField("");
			textField1.setText("Equation Section");
			text.setPrefWidth(500);
			text.setPrefHeight(300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			//here we load the load if it is Balanced
			//the file is Balanced if each open tag has it is closed tag
			load.setOnAction(event -> {
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
				if (selectedFile != null) {
					String newpath = selectedFile.getAbsolutePath();
					path.setText(newpath);
					File searchFile = new File(path.getText());
					CursorStack<String> stack1 = new CursorStack<>(100);

					try (BufferedReader reader = new BufferedReader(new FileReader(searchFile))) {
						String line;
						boolean isBalanced = true;

						int count = 1;
						while ((line = reader.readLine()) != null) {
							line = line.trim();
							boolean startReading = false;
							if ("<242>".equals(line) || "<section>".equals(line) || "<infix>".equals(line)
									|| "<postfix>".equals(line)) {

								stack1.push(line);
								System.out.println("Pushed: " + line);
							}
							if (line.contains("<equation>")) {

								stack1.push("<equation>");
								System.out.println("Pushed: " + "<equation>");

							}

							if ("</242>".equals(line) || "</section>".equals(line) || "</infix>".equals(line)
									|| "</postfix>".equals(line)) {
								if (stack1.isEmpty()) {
									isBalanced = false;
								} else {
									String opening = stack1.pop();
									System.out.println("Popped: " + opening);
									isBalanced = arePaired(opening, line);
									System.out.println(isBalanced);
								}
							}
							if (line.contains("</equation>")) {
								if (stack1.isEmpty()) {
									isBalanced = false;
								} else {
									String opening = stack1.pop();
									System.out.println("Popped: " + opening);
									isBalanced = arePaired(opening, "</equation>");
									System.out.println(isBalanced);

								}
							}
							

						}
						if(!stack1.isEmpty()) {
							isBalanced = false;
						}
						System.out.println("the result of balanced is  :" + isBalanced);
						
						
						//if The result of Balanced is true the Equation loaded and make the converting
						if (isBalanced) {

							StringBuilder equation = new StringBuilder();
							String filePath = "\\C:\\Users\\DELL\\Downloads\\2.txt\\";

							BufferedReader reade = new BufferedReader(new FileReader(filePath));
							String lin;
							String openTag = "<equation>";
							String closeTag = "</equation>";
							String lineAfterSectionEnd = new String("");
							StringBuilder EQUATION = new StringBuilder();
							StringBuilder EQUATION1 = new StringBuilder();

							boolean startReading = false;

							while ((lin = reade.readLine()) != null) {
								lin = lin.trim();
								// The First Section
								//the first section in section one is in infix Expression we convert it in postfix Expression 
								if (!lin.contains("</section>")) {

									if (!lin.contains("</infix>")) {
										lin = lin.trim();
										for (int index = 0; index < lin.length(); index++) {
											char currentCharacter = lin.charAt(index);

											if (currentCharacter == '>') {
												startReading = true;
												index++;

												while (index + closeTag.length() - 1 < lin.length() && !lin
														.substring(index, index + closeTag.length()).equals(closeTag)) {
													equation.append(lin.charAt(index));
													index++;
												}
												if (!equation.isEmpty()) {
													System.out.println(equation.toString());
													System.out.println(infixToPostfix(equation.toString()) + ">>>");
													text.appendText(equation.toString() + "==>"
															+ infixToPostfix(equation.toString()) + "==>"
															+ postfixEvaluate(infixToPostfix(equation.toString()))
															+ "\n ");
													first.appendText(equation.toString() + "==>"
															+ infixToPostfix(equation.toString()) + "==>"
															+ postfixEvaluate(infixToPostfix(equation.toString()))
															+ "\n ");

												}
												equation.setLength(0); // Clear the StringBuilder for the next equation
												startReading = false; // Reset the flag
											}
										}
									}
                                // and the second section in first section is in postFix Expression and we converte it to prefix expression
									else if (lin.contains("</infix>")) {
										while ((lin = reade.readLine()) != null) {
											if (!lin.contains("</postfix>")) {
												lin = lin.trim();

												for (int index = 0; index < lin.length(); index++) {
													char currentCharacter = lin.charAt(index);

													if (currentCharacter == '>') {
														startReading = true;
														index++;

														while (index + closeTag.length() - 1 < lin.length()
																&& !lin.substring(index, index + closeTag.length())
																		.equals(closeTag)) {
															equation.append(lin.charAt(index));
															index++;
														}
														if (!equation.isEmpty()) {
															// System.out.println(postFixToPreFix(equation.toString())
															// +" The pre");
															text.appendText(equation.toString() + "==>"
																	+ postFixToPreFix(equation.toString()) + "==>"
																	+ PrefixEvaluation(
																			postFixToPreFix(equation.toString()))
																	+ "\n");
															first.appendText(equation.toString() + "==>"
																	+ postFixToPreFix(equation.toString()) + "==>"
																	+ PrefixEvaluation(
																			postFixToPreFix(equation.toString()))
																	+ "\n");

														}
														equation.setLength(0); // Clear the StringBuilder for the next
																				// equation
														startReading = false; // Reset the flag
													}
												}
											} else {
												break;
											}
										}

									}
								}
								// The Second Section
								//the second section is like the first section have two section the first section in infix expression and the second in postfx
								else if (lin.contains("</section>")) {
									while ((lin = reade.readLine()) != null) {
										if (!lin.contains("</infix>")) {
											lin = lin.trim();
											for (int index = 0; index < lin.length(); index++) {
												char currentCharacter = lin.charAt(index);

												if (currentCharacter == '>') {
													startReading = true;
													index++;

													while (index + closeTag.length() - 1 < lin.length()
															&& !lin.substring(index, index + closeTag.length())
																	.equals(closeTag)) {
														EQUATION.append(lin.charAt(index));
														index++;
													}
													if (!EQUATION.isEmpty()) {
														System.out.println(EQUATION.toString());
														System.out.println(
																infixToPostfix(EQUATION.toString()) + "  .........");
														textField2.appendText(EQUATION.toString() + "==>"
																+ infixToPostfix(EQUATION.toString()) + "==>"
																+ postfixEvaluate(infixToPostfix(EQUATION.toString()))
																+ "\n");
														// infixToPostfix(EQUATION.toString());

													}
													EQUATION.setLength(0); // Clear the StringBuilder for the next
																			// equation
													startReading = false; // Reset the flag
												}
											}
										}

										else if (lin.contains("</infix>")) {
											while ((lin = reade.readLine()) != null) {
												if (!lin.contains("</postfix>")) {
													lin = lin.trim();

													for (int index = 0; index < lin.length(); index++) {
														char currentCharacter = lin.charAt(index);

														if (currentCharacter == '>') {
															startReading = true;
															index++;

															while (index + closeTag.length() - 1 < lin.length()
																	&& !lin.substring(index, index + closeTag.length())
																			.equals(closeTag)) {
																EQUATION1.append(lin.charAt(index));
																index++;
															}
															if (!EQUATION1.isEmpty()) {

																// System.out.println(equation);
																postFixToPreFix(EQUATION1.toString());
																textField2.appendText(EQUATION1.toString() + "==>"
																		+ postFixToPreFix(EQUATION1.toString()) + "==>"
																		+ PrefixEvaluation(
																				postFixToPreFix(EQUATION1.toString()))
																		+ " \n");

															}
															EQUATION1.setLength(0); // Clear the StringBuilder for the

															startReading = false; // Reset the flag
														}
													}
												} else {
													break;
												}
											}

										}

									}

								}

							}
							Next.setOnAction(even -> {
								text.clear();
								text.setText(textField2.getText() + "\n");
								

							});
							Prev.setOnAction(even -> {
								text.clear();
								text.setText(first.getText() + "\n");

							});
							reader.close();
						}

						else {
							System.out.println("Sorry But The File does not Balanced !!!!!!!");
							text.setText("Sorry but The File Does not Balanced");
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			VBox v = new VBox(10);
			v.getChildren().addAll(textField1, text);
			root.setTop(hbox);
			root.setBottom(PN);
			root.setCenter(v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static boolean arePaired(String openDelimiter, String closeDelimiter) {

		return ((openDelimiter.equals("<242>") && closeDelimiter.equals("</242>"))
				|| (openDelimiter.equals("<section>") && closeDelimiter.equals("</section>"))
				|| (openDelimiter.equals("<infix>") && closeDelimiter.equals("</infix>"))
				|| (openDelimiter.equals("<postfix>") && closeDelimiter.equals("</postfix>"))
				|| (openDelimiter.equals("<equation>") && closeDelimiter.equals("</equation>")));
	}
//here we convert the expression in infixexpression to postfixexpression
	public static String infixToPostfix(String textFromTextField2) {
		CursorStack<Character> stack2 = new CursorStack<>(100);
		StringBuilder postfix = new StringBuilder();

		String[] tokens = textFromTextField2.split(" ");

		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];

			if (!token.equals("+") && !token.equals("-") && !token.equals("/") && !token.equals("*")
					&& !token.equals("^") && !token.equals("(") && !token.equals(")")) {
				postfix.append(token).append(' ');
			} else if (token.equals("^")) {
				stack2.push('^');
			} else if (token.equals("+") || token.equals("-") || token.equals("/") || token.equals("*")) {
				while (!stack2.isEmpty() && getPriority(token.charAt(0)) <= getPriority(stack2.peek())) {
					postfix.append(stack2.pop()).append(' ');
				}
				stack2.push(token.charAt(0));
			} else if (token.equals("(")) {
				stack2.push('(');
			} else if (token.equals(")")) {
				char topOperator;
				while (!stack2.isEmpty() && (topOperator = stack2.pop()) != '(') {
					postfix.append(topOperator).append(' ');
				}
			}
		}

		while (!stack2.isEmpty()) {
			postfix.append(stack2.pop()).append(' ');
		}

		return postfix.toString();
	}

	public static double postfixEvaluate(String postfixExpression) {
		CursorStack<Double> stack = new CursorStack<>(100);

		String[] tokens = postfixExpression.split(" ");

		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (!token.isEmpty()) {
				if (!token.equals("+") && !token.equals("-") && !token.equals("/") && !token.equals("*")
						&& !token.equals("^") && !token.equals("(") && !token.equals(")")) {
					// If the token is a number, push it onto the stack
					stack.push(Double.parseDouble(token));
				} else {
					// If the token is an operator, pop operands from the stack and perform the
					// operation
					Double operand2 = stack.pop();
					Double operand1 = stack.pop();
					
					if (token.equals("+")) {
					    stack.push(operand1 + operand2);
					} else if (token.equals("-")) {
					    stack.push(operand1 - operand2);
					} else if (token.equals("*")) {
					    stack.push(operand1 * operand2);
					} else if (token.equals("/")) {
					    stack.push(operand1 / operand2);
					} else if (token.equals("^")) {
						stack.push(Math.pow(operand1, operand2));
					} else {
					    // Handle other operators as needed
					    throw new IllegalArgumentException("Invalid operator: " + token);
					}

				}
			}
		}

		// Ensure the stack is not empty
		if (stack.isEmpty()) {
			throw new IllegalArgumentException("Invalid postfix expression");
		}

		// The result should be the only element remaining on the stack
		return stack.pop();
	}

	public static double PrefixEvaluation(String PrefixExpression) {
		CursorStack<Double> stack5 = new CursorStack<>(100);
		String[] prefix = PrefixExpression.split(" ");

		for (int i = prefix.length - 1; i >= 0; i--) {
			String token = prefix[i];
			if (!token.isEmpty()) {
				if (!token.equals("+") && !token.equals("-") && !token.equals("/") && !token.equals("*")
						&& !token.equals("^") && !token.equals("(") && !token.equals(")")) {
					// If the token is a number, push it onto the stack
					stack5.push(Double.parseDouble(token));
				} else {
					// If the token is an operator, pop operands from the stack and perform the
					// operation
					Double operand2 = stack5.pop();
					Double operand1 = stack5.pop();

					switch (token) {
					case "+":
						stack5.push(operand1 + operand2);
						break;
					case "-":
						stack5.push(operand1 - operand2);
						break;
					case "*":
						stack5.push(operand1 * operand2);
						break;
					case "/":
						stack5.push(operand1 / operand2);
						break;
					case "^":
						stack5.push(Math.pow(operand1, operand2));
						break;
					// Handle other operators as needed
					default:
						throw new IllegalArgumentException("Invalid operator: " + token);
					}
				}
			}
		}

		// Ensure the stack is not empty
		if (stack5.isEmpty()) {
			throw new IllegalArgumentException("Invalid postfix expression");
		}

		// The result should be the only element remaining on the stack
		return stack5.pop();
	}

	// Utility method to check if a string is a double
	private static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static int getPriority(char Oper) {
		switch (Oper) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return 0;
		}
	}

	public static String postFixToPreFix(String postfixExpression) {
		CursorStack<String> stack3 = new CursorStack<>(1000);

		String[] postfix = postfixExpression.split(" ");

		for (int i = 0; i < postfix.length; i++) {
			String string = postfix[i];
			if (!string.equals("+") && !string.equals("-") && !string.equals("/") && !string.equals("*")
					&& !string.equals("^") && !string.equals("(") && !string.equals(")")) {
				stack3.push(string);
			} else if (string.equals("+") || string.equals("-") || string.equals("/") || string.equals("*")
					|| string.equals("^") || string.equals("(") || string.equals(")")) {
				String secondOperand = stack3.pop();
				String firstOperand = stack3.pop();
				String result = string + " " + firstOperand + " " + secondOperand;
				stack3.push(result);
			}
		}

		return stack3.peek();
	}

}
