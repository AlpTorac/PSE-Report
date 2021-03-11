# Details

Date : 2021-02-19 20:42:13

Directory c:\Users\Casper\git\PSE-Report\Implementation Phase\GELF\src\main\java\gelf

Total : 178 files,  12211 codes, 1844 comments, 2777 blanks, all 16832 lines

[summary](results.md)

## Files
| filename | language | code | comment | blank | total |
| :--- | :--- | ---: | ---: | ---: | ---: |
| [src/main/java/gelf/controller/Event.java](/src/main/java/gelf/controller/Event.java) | Java | 24 | 63 | 25 | 112 |
| [src/main/java/gelf/controller/EventManager.java](/src/main/java/gelf/controller/EventManager.java) | Java | 72 | 16 | 10 | 98 |
| [src/main/java/gelf/controller/listeners/CompareListener.java](/src/main/java/gelf/controller/listeners/CompareListener.java) | Java | 53 | 9 | 12 | 74 |
| [src/main/java/gelf/controller/listeners/CopyListener.java](/src/main/java/gelf/controller/listeners/CopyListener.java) | Java | 37 | 8 | 11 | 56 |
| [src/main/java/gelf/controller/listeners/DeleteCellListener.java](/src/main/java/gelf/controller/listeners/DeleteCellListener.java) | Java | 37 | 8 | 10 | 55 |
| [src/main/java/gelf/controller/listeners/EditListener.java](/src/main/java/gelf/controller/listeners/EditListener.java) | Java | 63 | 16 | 13 | 92 |
| [src/main/java/gelf/controller/listeners/LoadListener.java](/src/main/java/gelf/controller/listeners/LoadListener.java) | Java | 19 | 4 | 7 | 30 |
| [src/main/java/gelf/controller/listeners/LoadProjectListener.java](/src/main/java/gelf/controller/listeners/LoadProjectListener.java) | Java | 11 | 5 | 5 | 21 |
| [src/main/java/gelf/controller/listeners/MergeListener.java](/src/main/java/gelf/controller/listeners/MergeListener.java) | Java | 40 | 8 | 8 | 56 |
| [src/main/java/gelf/controller/listeners/MoveListener.java](/src/main/java/gelf/controller/listeners/MoveListener.java) | Java | 32 | 9 | 10 | 51 |
| [src/main/java/gelf/controller/listeners/OpenElementListener.java](/src/main/java/gelf/controller/listeners/OpenElementListener.java) | Java | 56 | 9 | 20 | 85 |
| [src/main/java/gelf/controller/listeners/PasteListener.java](/src/main/java/gelf/controller/listeners/PasteListener.java) | Java | 30 | 8 | 8 | 46 |
| [src/main/java/gelf/controller/listeners/PropertiesListener.java](/src/main/java/gelf/controller/listeners/PropertiesListener.java) | Java | 19 | 0 | 10 | 29 |
| [src/main/java/gelf/controller/listeners/RedoListener.java](/src/main/java/gelf/controller/listeners/RedoListener.java) | Java | 18 | 4 | 7 | 29 |
| [src/main/java/gelf/controller/listeners/RemoveListener.java](/src/main/java/gelf/controller/listeners/RemoveListener.java) | Java | 45 | 8 | 8 | 61 |
| [src/main/java/gelf/controller/listeners/RenameListener.java](/src/main/java/gelf/controller/listeners/RenameListener.java) | Java | 84 | 14 | 15 | 113 |
| [src/main/java/gelf/controller/listeners/SaveAllListener.java](/src/main/java/gelf/controller/listeners/SaveAllListener.java) | Java | 30 | 5 | 8 | 43 |
| [src/main/java/gelf/controller/listeners/SaveAsListener.java](/src/main/java/gelf/controller/listeners/SaveAsListener.java) | Java | 66 | 8 | 10 | 84 |
| [src/main/java/gelf/controller/listeners/SaveListener.java](/src/main/java/gelf/controller/listeners/SaveListener.java) | Java | 69 | 8 | 11 | 88 |
| [src/main/java/gelf/controller/listeners/ScaleListener.java](/src/main/java/gelf/controller/listeners/ScaleListener.java) | Java | 31 | 10 | 19 | 60 |
| [src/main/java/gelf/controller/listeners/SearchListener.java](/src/main/java/gelf/controller/listeners/SearchListener.java) | Java | 53 | 15 | 19 | 87 |
| [src/main/java/gelf/controller/listeners/UndoListener.java](/src/main/java/gelf/controller/listeners/UndoListener.java) | Java | 10 | 4 | 5 | 19 |
| [src/main/java/gelf/model/commands/Command.java](/src/main/java/gelf/model/commands/Command.java) | Java | 6 | 12 | 5 | 23 |
| [src/main/java/gelf/model/commands/CommandHistory.java](/src/main/java/gelf/model/commands/CommandHistory.java) | Java | 67 | 44 | 16 | 127 |
| [src/main/java/gelf/model/commands/ConflictData.java](/src/main/java/gelf/model/commands/ConflictData.java) | Java | 15 | 18 | 6 | 39 |
| [src/main/java/gelf/model/commands/CreateLibraryCommand.java](/src/main/java/gelf/model/commands/CreateLibraryCommand.java) | Java | 22 | 16 | 6 | 44 |
| [src/main/java/gelf/model/commands/DeleteCommand.java](/src/main/java/gelf/model/commands/DeleteCommand.java) | Java | 28 | 15 | 6 | 49 |
| [src/main/java/gelf/model/commands/MergeCommand.java](/src/main/java/gelf/model/commands/MergeCommand.java) | Java | 55 | 16 | 7 | 78 |
| [src/main/java/gelf/model/commands/MoveCommand.java](/src/main/java/gelf/model/commands/MoveCommand.java) | Java | 74 | 20 | 14 | 108 |
| [src/main/java/gelf/model/commands/NameConflictResolver.java](/src/main/java/gelf/model/commands/NameConflictResolver.java) | Java | 67 | 24 | 7 | 98 |
| [src/main/java/gelf/model/commands/OpenFileCommand.java](/src/main/java/gelf/model/commands/OpenFileCommand.java) | Java | 56 | 13 | 12 | 81 |
| [src/main/java/gelf/model/commands/PasteCommand.java](/src/main/java/gelf/model/commands/PasteCommand.java) | Java | 74 | 20 | 13 | 107 |
| [src/main/java/gelf/model/commands/RenameCommand.java](/src/main/java/gelf/model/commands/RenameCommand.java) | Java | 23 | 15 | 5 | 43 |
| [src/main/java/gelf/model/commands/ResolutionMethod.java](/src/main/java/gelf/model/commands/ResolutionMethod.java) | Java | 8 | 5 | 2 | 15 |
| [src/main/java/gelf/model/commands/ScaleCommand.java](/src/main/java/gelf/model/commands/ScaleCommand.java) | Java | 21 | 15 | 6 | 42 |
| [src/main/java/gelf/model/commands/TextEditCommand.java](/src/main/java/gelf/model/commands/TextEditCommand.java) | Java | 68 | 28 | 10 | 106 |
| [src/main/java/gelf/model/compilers/LibertyCompiler.java](/src/main/java/gelf/model/compilers/LibertyCompiler.java) | Java | 140 | 35 | 10 | 185 |
| [src/main/java/gelf/model/elements/Cell.java](/src/main/java/gelf/model/elements/Cell.java) | Java | 455 | 95 | 64 | 614 |
| [src/main/java/gelf/model/elements/CompareElementByName.java](/src/main/java/gelf/model/elements/CompareElementByName.java) | Java | 7 | 11 | 4 | 22 |
| [src/main/java/gelf/model/elements/Element.java](/src/main/java/gelf/model/elements/Element.java) | Java | 42 | 4 | 15 | 61 |
| [src/main/java/gelf/model/elements/HigherElement.java](/src/main/java/gelf/model/elements/HigherElement.java) | Java | 115 | 5 | 32 | 152 |
| [src/main/java/gelf/model/elements/InputPin.java](/src/main/java/gelf/model/elements/InputPin.java) | Java | 75 | 15 | 13 | 103 |
| [src/main/java/gelf/model/elements/Library.java](/src/main/java/gelf/model/elements/Library.java) | Java | 378 | 31 | 72 | 481 |
| [src/main/java/gelf/model/elements/OutputPin.java](/src/main/java/gelf/model/elements/OutputPin.java) | Java | 184 | 15 | 29 | 228 |
| [src/main/java/gelf/model/elements/Pin.java](/src/main/java/gelf/model/elements/Pin.java) | Java | 55 | 4 | 20 | 79 |
| [src/main/java/gelf/model/elements/Stat.java](/src/main/java/gelf/model/elements/Stat.java) | Java | 37 | 4 | 11 | 52 |
| [src/main/java/gelf/model/elements/attributes/Attribute.java](/src/main/java/gelf/model/elements/attributes/Attribute.java) | Java | 13 | 5 | 7 | 25 |
| [src/main/java/gelf/model/elements/attributes/InAttribute.java](/src/main/java/gelf/model/elements/attributes/InAttribute.java) | Java | 77 | 11 | 21 | 109 |
| [src/main/java/gelf/model/elements/attributes/InputPower.java](/src/main/java/gelf/model/elements/attributes/InputPower.java) | Java | 21 | 4 | 5 | 30 |
| [src/main/java/gelf/model/elements/attributes/Leakage.java](/src/main/java/gelf/model/elements/attributes/Leakage.java) | Java | 85 | 12 | 21 | 118 |
| [src/main/java/gelf/model/elements/attributes/OutAttribute.java](/src/main/java/gelf/model/elements/attributes/OutAttribute.java) | Java | 108 | 12 | 27 | 147 |
| [src/main/java/gelf/model/elements/attributes/OutputPower.java](/src/main/java/gelf/model/elements/attributes/OutputPower.java) | Java | 38 | 4 | 10 | 52 |
| [src/main/java/gelf/model/elements/attributes/PowerGroup.java](/src/main/java/gelf/model/elements/attributes/PowerGroup.java) | Java | 13 | 0 | 2 | 15 |
| [src/main/java/gelf/model/elements/attributes/Timing.java](/src/main/java/gelf/model/elements/attributes/Timing.java) | Java | 56 | 4 | 15 | 75 |
| [src/main/java/gelf/model/elements/attributes/TimingGroup.java](/src/main/java/gelf/model/elements/attributes/TimingGroup.java) | Java | 15 | 0 | 2 | 17 |
| [src/main/java/gelf/model/elements/attributes/TimingKey.java](/src/main/java/gelf/model/elements/attributes/TimingKey.java) | Java | 29 | 5 | 9 | 43 |
| [src/main/java/gelf/model/elements/attributes/TimingSense.java](/src/main/java/gelf/model/elements/attributes/TimingSense.java) | Java | 14 | 0 | 2 | 16 |
| [src/main/java/gelf/model/elements/attributes/TimingType.java](/src/main/java/gelf/model/elements/attributes/TimingType.java) | Java | 20 | 0 | 2 | 22 |
| [src/main/java/gelf/model/exceptions/ExceedingFileSizeException.java](/src/main/java/gelf/model/exceptions/ExceedingFileSizeException.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/model/exceptions/InvalidArgumentException.java](/src/main/java/gelf/model/exceptions/InvalidArgumentException.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/model/exceptions/InvalidComparisonException.java](/src/main/java/gelf/model/exceptions/InvalidComparisonException.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/model/exceptions/InvalidFileFormatException.java](/src/main/java/gelf/model/exceptions/InvalidFileFormatException.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/model/exceptions/InvalidNameException.java](/src/main/java/gelf/model/exceptions/InvalidNameException.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/model/exceptions/TooManyPanelsOpenedException.java](/src/main/java/gelf/model/exceptions/TooManyPanelsOpenedException.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/model/exceptions/TooManySelectedException.java](/src/main/java/gelf/model/exceptions/TooManySelectedException.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/model/parsers/LibertyParser.java](/src/main/java/gelf/model/parsers/LibertyParser.java) | Java | 606 | 215 | 34 | 855 |
| [src/main/java/gelf/model/project/FileChooserOpen.java](/src/main/java/gelf/model/project/FileChooserOpen.java) | Java | 70 | 0 | 12 | 82 |
| [src/main/java/gelf/model/project/FileChooserSave.java](/src/main/java/gelf/model/project/FileChooserSave.java) | Java | 69 | 0 | 12 | 81 |
| [src/main/java/gelf/model/project/FileManager.java](/src/main/java/gelf/model/project/FileManager.java) | Java | 50 | 5 | 16 | 71 |
| [src/main/java/gelf/model/project/Interpolator.java](/src/main/java/gelf/model/project/Interpolator.java) | Java | 82 | 34 | 7 | 123 |
| [src/main/java/gelf/model/project/Model.java](/src/main/java/gelf/model/project/Model.java) | Java | 48 | 37 | 12 | 97 |
| [src/main/java/gelf/model/project/Project.java](/src/main/java/gelf/model/project/Project.java) | Java | 52 | 86 | 15 | 153 |
| [src/main/java/gelf/model/project/Updatable.java](/src/main/java/gelf/model/project/Updatable.java) | Java | 4 | 8 | 2 | 14 |
| [src/main/java/gelf/view/App.java](/src/main/java/gelf/view/App.java) | Java | 8 | 7 | 3 | 18 |
| [src/main/java/gelf/view/components/AutoResizing.java](/src/main/java/gelf/view/components/AutoResizing.java) | Java | 6 | 4 | 2 | 12 |
| [src/main/java/gelf/view/components/Button.java](/src/main/java/gelf/view/components/Button.java) | Java | 64 | 0 | 13 | 77 |
| [src/main/java/gelf/view/components/Checkbox.java](/src/main/java/gelf/view/components/Checkbox.java) | Java | 10 | 3 | 3 | 16 |
| [src/main/java/gelf/view/components/DropdownSelector.java](/src/main/java/gelf/view/components/DropdownSelector.java) | Java | 7 | 3 | 3 | 13 |
| [src/main/java/gelf/view/components/InputBox.java](/src/main/java/gelf/view/components/InputBox.java) | Java | 4 | 3 | 4 | 11 |
| [src/main/java/gelf/view/components/Label.java](/src/main/java/gelf/view/components/Label.java) | Java | 31 | 0 | 9 | 40 |
| [src/main/java/gelf/view/components/Menu.java](/src/main/java/gelf/view/components/Menu.java) | Java | 77 | 0 | 16 | 93 |
| [src/main/java/gelf/view/components/MenuBar.java](/src/main/java/gelf/view/components/MenuBar.java) | Java | 7 | 3 | 2 | 12 |
| [src/main/java/gelf/view/components/MenuItem.java](/src/main/java/gelf/view/components/MenuItem.java) | Java | 55 | 3 | 9 | 67 |
| [src/main/java/gelf/view/components/Panel.java](/src/main/java/gelf/view/components/Panel.java) | Java | 45 | 7 | 6 | 58 |
| [src/main/java/gelf/view/components/ResizeMode.java](/src/main/java/gelf/view/components/ResizeMode.java) | Java | 6 | 0 | 1 | 7 |
| [src/main/java/gelf/view/components/Resizer.java](/src/main/java/gelf/view/components/Resizer.java) | Java | 57 | 7 | 5 | 69 |
| [src/main/java/gelf/view/components/ScrollPane.java](/src/main/java/gelf/view/components/ScrollPane.java) | Java | 4 | 3 | 4 | 11 |
| [src/main/java/gelf/view/components/TextPane.java](/src/main/java/gelf/view/components/TextPane.java) | Java | 4 | 3 | 4 | 11 |
| [src/main/java/gelf/view/components/Tree.java](/src/main/java/gelf/view/components/Tree.java) | Java | 4 | 3 | 4 | 11 |
| [src/main/java/gelf/view/components/TreeMouseAdapter.java](/src/main/java/gelf/view/components/TreeMouseAdapter.java) | Java | 92 | 24 | 11 | 127 |
| [src/main/java/gelf/view/components/Window.java](/src/main/java/gelf/view/components/Window.java) | Java | 44 | 8 | 6 | 58 |
| [src/main/java/gelf/view/composites/ColorTheme.java](/src/main/java/gelf/view/composites/ColorTheme.java) | Java | 14 | 0 | 5 | 19 |
| [src/main/java/gelf/view/composites/Comparer.java](/src/main/java/gelf/view/composites/Comparer.java) | Java | 626 | 121 | 91 | 838 |
| [src/main/java/gelf/view/composites/ElementManipulator.java](/src/main/java/gelf/view/composites/ElementManipulator.java) | Java | 28 | 3 | 9 | 40 |
| [src/main/java/gelf/view/composites/InfoBar.java](/src/main/java/gelf/view/composites/InfoBar.java) | Java | 52 | 15 | 8 | 75 |
| [src/main/java/gelf/view/composites/InfoBarID.java](/src/main/java/gelf/view/composites/InfoBarID.java) | Java | 7 | 0 | 1 | 8 |
| [src/main/java/gelf/view/composites/MainWindow.java](/src/main/java/gelf/view/composites/MainWindow.java) | Java | 159 | 37 | 24 | 220 |
| [src/main/java/gelf/view/composites/MergeDialog.java](/src/main/java/gelf/view/composites/MergeDialog.java) | Java | 179 | 16 | 42 | 237 |
| [src/main/java/gelf/view/composites/Outliner.java](/src/main/java/gelf/view/composites/Outliner.java) | Java | 141 | 19 | 19 | 179 |
| [src/main/java/gelf/view/composites/SettingsDialog.java](/src/main/java/gelf/view/composites/SettingsDialog.java) | Java | 7 | 3 | 3 | 13 |
| [src/main/java/gelf/view/composites/SubWindow.java](/src/main/java/gelf/view/composites/SubWindow.java) | Java | 155 | 13 | 18 | 186 |
| [src/main/java/gelf/view/composites/SubWindowArea.java](/src/main/java/gelf/view/composites/SubWindowArea.java) | Java | 53 | 8 | 7 | 68 |
| [src/main/java/gelf/view/composites/TextEditor.java](/src/main/java/gelf/view/composites/TextEditor.java) | Java | 202 | 66 | 48 | 316 |
| [src/main/java/gelf/view/composites/Visualizer.java](/src/main/java/gelf/view/composites/Visualizer.java) | Java | 688 | 36 | 88 | 812 |
| [src/main/java/gelf/view/diagrams/DiagramDirector.java](/src/main/java/gelf/view/diagrams/DiagramDirector.java) | Java | 20 | 0 | 8 | 28 |
| [src/main/java/gelf/view/diagrams/DiagramWizard.java](/src/main/java/gelf/view/diagrams/DiagramWizard.java) | Java | 160 | 0 | 27 | 187 |
| [src/main/java/gelf/view/diagrams/IDiagram.java](/src/main/java/gelf/view/diagrams/IDiagram.java) | Java | 44 | 0 | 22 | 66 |
| [src/main/java/gelf/view/diagrams/IDiagramOverlayer.java](/src/main/java/gelf/view/diagrams/IDiagramOverlayer.java) | Java | 12 | 0 | 3 | 15 |
| [src/main/java/gelf/view/diagrams/IDiagramViewHelper.java](/src/main/java/gelf/view/diagrams/IDiagramViewHelper.java) | Java | 7 | 0 | 5 | 12 |
| [src/main/java/gelf/view/diagrams/IDiagramWizard.java](/src/main/java/gelf/view/diagrams/IDiagramWizard.java) | Java | 29 | 0 | 22 | 51 |
| [src/main/java/gelf/view/diagrams/SettingsProvider.java](/src/main/java/gelf/view/diagrams/SettingsProvider.java) | Java | 265 | 7 | 89 | 361 |
| [src/main/java/gelf/view/diagrams/builder/BarChartBuilder.java](/src/main/java/gelf/view/diagrams/builder/BarChartBuilder.java) | Java | 55 | 0 | 13 | 68 |
| [src/main/java/gelf/view/diagrams/builder/ContainerAccessPoint.java](/src/main/java/gelf/view/diagrams/builder/ContainerAccessPoint.java) | Java | 5 | 0 | 3 | 8 |
| [src/main/java/gelf/view/diagrams/builder/DiagramBuilder.java](/src/main/java/gelf/view/diagrams/builder/DiagramBuilder.java) | Java | 144 | 1 | 34 | 179 |
| [src/main/java/gelf/view/diagrams/builder/FunctionGraphBuilder.java](/src/main/java/gelf/view/diagrams/builder/FunctionGraphBuilder.java) | Java | 38 | 0 | 11 | 49 |
| [src/main/java/gelf/view/diagrams/builder/HeatMapBuilder.java](/src/main/java/gelf/view/diagrams/builder/HeatMapBuilder.java) | Java | 28 | 0 | 9 | 37 |
| [src/main/java/gelf/view/diagrams/builder/HistogramBuilder.java](/src/main/java/gelf/view/diagrams/builder/HistogramBuilder.java) | Java | 38 | 0 | 12 | 50 |
| [src/main/java/gelf/view/diagrams/builder/IBarChartBuilder.java](/src/main/java/gelf/view/diagrams/builder/IBarChartBuilder.java) | Java | 32 | 0 | 12 | 44 |
| [src/main/java/gelf/view/diagrams/builder/IDiagramBuilder.java](/src/main/java/gelf/view/diagrams/builder/IDiagramBuilder.java) | Java | 31 | 0 | 10 | 41 |
| [src/main/java/gelf/view/diagrams/builder/IFunctionGraphBuilder.java](/src/main/java/gelf/view/diagrams/builder/IFunctionGraphBuilder.java) | Java | 28 | 0 | 10 | 38 |
| [src/main/java/gelf/view/diagrams/builder/IHeatMapBuilder.java](/src/main/java/gelf/view/diagrams/builder/IHeatMapBuilder.java) | Java | 85 | 9 | 28 | 122 |
| [src/main/java/gelf/view/diagrams/builder/IHistogramBuilder.java](/src/main/java/gelf/view/diagrams/builder/IHistogramBuilder.java) | Java | 49 | 0 | 20 | 69 |
| [src/main/java/gelf/view/diagrams/components/BarChartBar.java](/src/main/java/gelf/view/diagrams/components/BarChartBar.java) | Java | 23 | 0 | 9 | 32 |
| [src/main/java/gelf/view/diagrams/components/BiColorScale.java](/src/main/java/gelf/view/diagrams/components/BiColorScale.java) | Java | 45 | 0 | 14 | 59 |
| [src/main/java/gelf/view/diagrams/components/DescriptionLabel.java](/src/main/java/gelf/view/diagrams/components/DescriptionLabel.java) | Java | 16 | 0 | 5 | 21 |
| [src/main/java/gelf/view/diagrams/components/DiagramAxis.java](/src/main/java/gelf/view/diagrams/components/DiagramAxis.java) | Java | 256 | 8 | 79 | 343 |
| [src/main/java/gelf/view/diagrams/components/DiagramBar.java](/src/main/java/gelf/view/diagrams/components/DiagramBar.java) | Java | 94 | 3 | 35 | 132 |
| [src/main/java/gelf/view/diagrams/components/DiagramColorScale.java](/src/main/java/gelf/view/diagrams/components/DiagramColorScale.java) | Java | 143 | 3 | 51 | 197 |
| [src/main/java/gelf/view/diagrams/components/DiagramComponent.java](/src/main/java/gelf/view/diagrams/components/DiagramComponent.java) | Java | 80 | 0 | 24 | 104 |
| [src/main/java/gelf/view/diagrams/components/DiagramComponentFactory.java](/src/main/java/gelf/view/diagrams/components/DiagramComponentFactory.java) | Java | 57 | 0 | 12 | 69 |
| [src/main/java/gelf/view/diagrams/components/DiagramLabel.java](/src/main/java/gelf/view/diagrams/components/DiagramLabel.java) | Java | 92 | 3 | 26 | 121 |
| [src/main/java/gelf/view/diagrams/components/DiagramLine.java](/src/main/java/gelf/view/diagrams/components/DiagramLine.java) | Java | 108 | 3 | 39 | 150 |
| [src/main/java/gelf/view/diagrams/components/DiagramPoint.java](/src/main/java/gelf/view/diagrams/components/DiagramPoint.java) | Java | 86 | 9 | 29 | 124 |
| [src/main/java/gelf/view/diagrams/components/DiagramValueDisplayComponent.java](/src/main/java/gelf/view/diagrams/components/DiagramValueDisplayComponent.java) | Java | 38 | 0 | 15 | 53 |
| [src/main/java/gelf/view/diagrams/components/DiagramValueLabel.java](/src/main/java/gelf/view/diagrams/components/DiagramValueLabel.java) | Java | 107 | 3 | 31 | 141 |
| [src/main/java/gelf/view/diagrams/components/HasAttachablePart.java](/src/main/java/gelf/view/diagrams/components/HasAttachablePart.java) | Java | 6 | 0 | 4 | 10 |
| [src/main/java/gelf/view/diagrams/components/HeatMapLabel.java](/src/main/java/gelf/view/diagrams/components/HeatMapLabel.java) | Java | 22 | 0 | 8 | 30 |
| [src/main/java/gelf/view/diagrams/components/HistogramBar.java](/src/main/java/gelf/view/diagrams/components/HistogramBar.java) | Java | 17 | 0 | 4 | 21 |
| [src/main/java/gelf/view/diagrams/components/HoverLabel.java](/src/main/java/gelf/view/diagrams/components/HoverLabel.java) | Java | 159 | 3 | 37 | 199 |
| [src/main/java/gelf/view/diagrams/components/Hoverable.java](/src/main/java/gelf/view/diagrams/components/Hoverable.java) | Java | 51 | 0 | 13 | 64 |
| [src/main/java/gelf/view/diagrams/components/PositionIn2DDiagram.java](/src/main/java/gelf/view/diagrams/components/PositionIn2DDiagram.java) | Java | 52 | 12 | 18 | 82 |
| [src/main/java/gelf/view/diagrams/components/PositionInDiagram.java](/src/main/java/gelf/view/diagrams/components/PositionInDiagram.java) | Java | 58 | 14 | 20 | 92 |
| [src/main/java/gelf/view/diagrams/components/PositionInFrame.java](/src/main/java/gelf/view/diagrams/components/PositionInFrame.java) | Java | 43 | 0 | 13 | 56 |
| [src/main/java/gelf/view/diagrams/components/SolidAxis.java](/src/main/java/gelf/view/diagrams/components/SolidAxis.java) | Java | 15 | 0 | 5 | 20 |
| [src/main/java/gelf/view/diagrams/components/SolidLine.java](/src/main/java/gelf/view/diagrams/components/SolidLine.java) | Java | 12 | 0 | 4 | 16 |
| [src/main/java/gelf/view/diagrams/components/ValueDisplayPoint.java](/src/main/java/gelf/view/diagrams/components/ValueDisplayPoint.java) | Java | 13 | 0 | 4 | 17 |
| [src/main/java/gelf/view/diagrams/data/DiagramData.java](/src/main/java/gelf/view/diagrams/data/DiagramData.java) | Java | 182 | 0 | 54 | 236 |
| [src/main/java/gelf/view/diagrams/data/DiagramDataExtractionStrategy.java](/src/main/java/gelf/view/diagrams/data/DiagramDataExtractionStrategy.java) | Java | 13 | 0 | 6 | 19 |
| [src/main/java/gelf/view/diagrams/data/ValueIndexExtractor.java](/src/main/java/gelf/view/diagrams/data/ValueIndexExtractor.java) | Java | 63 | 0 | 23 | 86 |
| [src/main/java/gelf/view/diagrams/indicator/CoordinateIndicatorLine.java](/src/main/java/gelf/view/diagrams/indicator/CoordinateIndicatorLine.java) | Java | 34 | 3 | 10 | 47 |
| [src/main/java/gelf/view/diagrams/indicator/CoordinateIndicatorLineDisplayer.java](/src/main/java/gelf/view/diagrams/indicator/CoordinateIndicatorLineDisplayer.java) | Java | 31 | 0 | 11 | 42 |
| [src/main/java/gelf/view/diagrams/indicator/DiagramViewHelper.java](/src/main/java/gelf/view/diagrams/indicator/DiagramViewHelper.java) | Java | 72 | 0 | 19 | 91 |
| [src/main/java/gelf/view/diagrams/indicator/DiagramViewHelperFactory.java](/src/main/java/gelf/view/diagrams/indicator/DiagramViewHelperFactory.java) | Java | 33 | 0 | 12 | 45 |
| [src/main/java/gelf/view/diagrams/indicator/HelperLineDisplayer.java](/src/main/java/gelf/view/diagrams/indicator/HelperLineDisplayer.java) | Java | 20 | 0 | 7 | 27 |
| [src/main/java/gelf/view/diagrams/indicator/IndicatorIdentifier.java](/src/main/java/gelf/view/diagrams/indicator/IndicatorIdentifier.java) | Java | 9 | 0 | 2 | 11 |
| [src/main/java/gelf/view/diagrams/indicator/ValueLine.java](/src/main/java/gelf/view/diagrams/indicator/ValueLine.java) | Java | 29 | 3 | 11 | 43 |
| [src/main/java/gelf/view/diagrams/indicator/ValueLineDisplayer.java](/src/main/java/gelf/view/diagrams/indicator/ValueLineDisplayer.java) | Java | 45 | 0 | 11 | 56 |
| [src/main/java/gelf/view/diagrams/indicator/ViewHelperComponent.java](/src/main/java/gelf/view/diagrams/indicator/ViewHelperComponent.java) | Java | 29 | 0 | 8 | 37 |
| [src/main/java/gelf/view/diagrams/indicator/XCoordinateIndicatorLineDisplayer.java](/src/main/java/gelf/view/diagrams/indicator/XCoordinateIndicatorLineDisplayer.java) | Java | 28 | 0 | 8 | 36 |
| [src/main/java/gelf/view/diagrams/indicator/YCoordinateIndicatorLineDisplayer.java](/src/main/java/gelf/view/diagrams/indicator/YCoordinateIndicatorLineDisplayer.java) | Java | 28 | 0 | 9 | 37 |
| [src/main/java/gelf/view/diagrams/overlayer/BarChartOverlayStrategy.java](/src/main/java/gelf/view/diagrams/overlayer/BarChartOverlayStrategy.java) | Java | 45 | 0 | 14 | 59 |
| [src/main/java/gelf/view/diagrams/overlayer/DiagramOverlayStrategy.java](/src/main/java/gelf/view/diagrams/overlayer/DiagramOverlayStrategy.java) | Java | 221 | 2 | 76 | 299 |
| [src/main/java/gelf/view/diagrams/overlayer/DiagramOverlayer.java](/src/main/java/gelf/view/diagrams/overlayer/DiagramOverlayer.java) | Java | 62 | 0 | 19 | 81 |
| [src/main/java/gelf/view/diagrams/overlayer/FunctionGraphOverlayStrategy.java](/src/main/java/gelf/view/diagrams/overlayer/FunctionGraphOverlayStrategy.java) | Java | 74 | 0 | 24 | 98 |
| [src/main/java/gelf/view/diagrams/overlayer/HistogramOverlayStrategy.java](/src/main/java/gelf/view/diagrams/overlayer/HistogramOverlayStrategy.java) | Java | 55 | 0 | 18 | 73 |
| [src/main/java/gelf/view/diagrams/type/BarChart.java](/src/main/java/gelf/view/diagrams/type/BarChart.java) | Java | 12 | 0 | 3 | 15 |
| [src/main/java/gelf/view/diagrams/type/Diagram.java](/src/main/java/gelf/view/diagrams/type/Diagram.java) | Java | 165 | 0 | 45 | 210 |
| [src/main/java/gelf/view/diagrams/type/FunctionGraph.java](/src/main/java/gelf/view/diagrams/type/FunctionGraph.java) | Java | 12 | 0 | 3 | 15 |
| [src/main/java/gelf/view/diagrams/type/HeatMap.java](/src/main/java/gelf/view/diagrams/type/HeatMap.java) | Java | 12 | 0 | 3 | 15 |
| [src/main/java/gelf/view/diagrams/type/Histogram.java](/src/main/java/gelf/view/diagrams/type/Histogram.java) | Java | 12 | 0 | 3 | 15 |
| [src/main/java/gelf/view/representation/CellImageGenerator.java](/src/main/java/gelf/view/representation/CellImageGenerator.java) | Java | 73 | 12 | 10 | 95 |
| [src/main/java/gelf/view/representation/CellPanel.java](/src/main/java/gelf/view/representation/CellPanel.java) | Java | 356 | 46 | 74 | 476 |
| [src/main/java/gelf/view/representation/DataPanel.java](/src/main/java/gelf/view/representation/DataPanel.java) | Java | 108 | 25 | 23 | 156 |
| [src/main/java/gelf/view/representation/ICellImageGenerator.java](/src/main/java/gelf/view/representation/ICellImageGenerator.java) | Java | 5 | 8 | 5 | 18 |
| [src/main/java/gelf/view/representation/LibraryComparePanel.java](/src/main/java/gelf/view/representation/LibraryComparePanel.java) | Java | 87 | 9 | 23 | 119 |
| [src/main/java/gelf/view/representation/LibraryPanel.java](/src/main/java/gelf/view/representation/LibraryPanel.java) | Java | 85 | 19 | 21 | 125 |
| [src/main/java/gelf/view/representation/PinComparePanel.java](/src/main/java/gelf/view/representation/PinComparePanel.java) | Java | 93 | 12 | 13 | 118 |
| [src/main/java/gelf/view/representation/PinCompareSubPanel.java](/src/main/java/gelf/view/representation/PinCompareSubPanel.java) | Java | 129 | 27 | 21 | 177 |

[summary](results.md)