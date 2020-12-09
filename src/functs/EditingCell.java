package functs;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import model.Sale;

public class EditingCell extends TableCell<Sale, Integer> {

	private TextField textField;

	public EditingCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			createTextField();
			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		setText(Messages.getString("EditingCell.0") + getItem()); //$NON-NLS-1$
		setGraphic(null);
	}

	@Override
	public void updateItem(Integer item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			int row = getIndex();
			Sale sale = getTableView().getItems().get(row);

			if (empty) {
				setText(Messages.getString("EditingCell.1") + item); //$NON-NLS-1$
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
						// setGraphic(null);
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(null);
				}
			}
		}
	}

	private void createTextField() {

		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

		textField.setOnAction((e) -> commitEdit(Integer.parseInt(textField.getText())));

		textField.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

					if (!newValue) {
						System.out.println(Messages.getString("EditingCell.2") + textField.getText()); //$NON-NLS-1$
						commitEdit(Integer.parseInt(textField.getText()));
					}
				});

	}

	private String getString() {
		return getItem() == null ? Messages.getString("EditingCell.3") : Messages.getString("EditingCell.4") + getItem(); //$NON-NLS-1$ //$NON-NLS-2$
	}
}