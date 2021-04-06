# Details

Date : 2021-03-25 13:10:43

Directory c:\Users\Casper\git3\PSE-Report\Implementation Phase\GELF\src\main

Total : 157 files,  11880 codes, 3255 comments, 2351 blanks, all 17486 lines

[summary](results.md)

## Files
| filename | language | code | comment | blank | total |
| :--- | :--- | ---: | ---: | ---: | ---: |
| [main/java/gelf/controller/Event.java](/main/java/gelf/controller/Event.java) | Java | 24 | 63 | 25 | 112 |
| [main/java/gelf/controller/EventManager.java](/main/java/gelf/controller/EventManager.java) | Java | 68 | 16 | 10 | 94 |
| [main/java/gelf/controller/listeners/CompareListener.java](/main/java/gelf/controller/listeners/CompareListener.java) | Java | 67 | 9 | 12 | 88 |
| [main/java/gelf/controller/listeners/CopyListener.java](/main/java/gelf/controller/listeners/CopyListener.java) | Java | 41 | 8 | 11 | 60 |
| [main/java/gelf/controller/listeners/DeleteCellListener.java](/main/java/gelf/controller/listeners/DeleteCellListener.java) | Java | 58 | 8 | 12 | 78 |
| [main/java/gelf/controller/listeners/EditListener.java](/main/java/gelf/controller/listeners/EditListener.java) | Java | 33 | 10 | 16 | 59 |
| [main/java/gelf/controller/listeners/LoadListener.java](/main/java/gelf/controller/listeners/LoadListener.java) | Java | 19 | 4 | 7 | 30 |
| [main/java/gelf/controller/listeners/MergeListener.java](/main/java/gelf/controller/listeners/MergeListener.java) | Java | 45 | 8 | 9 | 62 |
| [main/java/gelf/controller/listeners/OpenElementListener.java](/main/java/gelf/controller/listeners/OpenElementListener.java) | Java | 54 | 9 | 20 | 83 |
| [main/java/gelf/controller/listeners/PasteListener.java](/main/java/gelf/controller/listeners/PasteListener.java) | Java | 35 | 8 | 8 | 51 |
| [main/java/gelf/controller/listeners/PropertiesListener.java](/main/java/gelf/controller/listeners/PropertiesListener.java) | Java | 33 | 0 | 11 | 44 |
| [main/java/gelf/controller/listeners/RedoListener.java](/main/java/gelf/controller/listeners/RedoListener.java) | Java | 24 | 4 | 7 | 35 |
| [main/java/gelf/controller/listeners/RemoveListener.java](/main/java/gelf/controller/listeners/RemoveListener.java) | Java | 70 | 8 | 10 | 88 |
| [main/java/gelf/controller/listeners/RenameListener.java](/main/java/gelf/controller/listeners/RenameListener.java) | Java | 78 | 14 | 16 | 108 |
| [main/java/gelf/controller/listeners/SaveAllListener.java](/main/java/gelf/controller/listeners/SaveAllListener.java) | Java | 30 | 5 | 8 | 43 |
| [main/java/gelf/controller/listeners/SaveAsListener.java](/main/java/gelf/controller/listeners/SaveAsListener.java) | Java | 63 | 8 | 11 | 82 |
| [main/java/gelf/controller/listeners/SaveListener.java](/main/java/gelf/controller/listeners/SaveListener.java) | Java | 60 | 8 | 11 | 79 |
| [main/java/gelf/controller/listeners/ScaleListener.java](/main/java/gelf/controller/listeners/ScaleListener.java) | Java | 30 | 8 | 15 | 53 |
| [main/java/gelf/controller/listeners/SearchListener.java](/main/java/gelf/controller/listeners/SearchListener.java) | Java | 46 | 15 | 19 | 80 |
| [main/java/gelf/controller/listeners/UndoListener.java](/main/java/gelf/controller/listeners/UndoListener.java) | Java | 18 | 4 | 6 | 28 |
| [main/java/gelf/model/commands/Command.java](/main/java/gelf/model/commands/Command.java) | Java | 6 | 16 | 5 | 27 |
| [main/java/gelf/model/commands/CommandHistory.java](/main/java/gelf/model/commands/CommandHistory.java) | Java | 68 | 57 | 15 | 140 |
| [main/java/gelf/model/commands/ConflictData.java](/main/java/gelf/model/commands/ConflictData.java) | Java | 15 | 22 | 6 | 43 |
| [main/java/gelf/model/commands/DeleteCommand.java](/main/java/gelf/model/commands/DeleteCommand.java) | Java | 28 | 17 | 7 | 52 |
| [main/java/gelf/model/commands/MergeCommand.java](/main/java/gelf/model/commands/MergeCommand.java) | Java | 56 | 18 | 7 | 81 |
| [main/java/gelf/model/commands/NameConflictResolver.java](/main/java/gelf/model/commands/NameConflictResolver.java) | Java | 67 | 29 | 7 | 103 |
| [main/java/gelf/model/commands/OpenFileCommand.java](/main/java/gelf/model/commands/OpenFileCommand.java) | Java | 60 | 15 | 14 | 89 |
| [main/java/gelf/model/commands/PasteCommand.java](/main/java/gelf/model/commands/PasteCommand.java) | Java | 72 | 24 | 13 | 109 |
| [main/java/gelf/model/commands/RenameCommand.java](/main/java/gelf/model/commands/RenameCommand.java) | Java | 23 | 17 | 8 | 48 |
| [main/java/gelf/model/commands/ResolutionMethod.java](/main/java/gelf/model/commands/ResolutionMethod.java) | Java | 4 | 6 | 2 | 12 |
| [main/java/gelf/model/commands/ScaleCommand.java](/main/java/gelf/model/commands/ScaleCommand.java) | Java | 86 | 15 | 6 | 107 |
| [main/java/gelf/model/commands/TextEditCommand.java](/main/java/gelf/model/commands/TextEditCommand.java) | Java | 63 | 32 | 10 | 105 |
| [main/java/gelf/model/compilers/LibertyCompiler.java](/main/java/gelf/model/compilers/LibertyCompiler.java) | Java | 137 | 43 | 10 | 190 |
| [main/java/gelf/model/elements/Cell.java](/main/java/gelf/model/elements/Cell.java) | Java | 513 | 95 | 67 | 675 |
| [main/java/gelf/model/elements/CompareElementByName.java](/main/java/gelf/model/elements/CompareElementByName.java) | Java | 7 | 11 | 4 | 22 |
| [main/java/gelf/model/elements/Element.java](/main/java/gelf/model/elements/Element.java) | Java | 49 | 4 | 18 | 71 |
| [main/java/gelf/model/elements/HigherElement.java](/main/java/gelf/model/elements/HigherElement.java) | Java | 122 | 5 | 34 | 161 |
| [main/java/gelf/model/elements/InputPin.java](/main/java/gelf/model/elements/InputPin.java) | Java | 85 | 15 | 14 | 114 |
| [main/java/gelf/model/elements/Library.java](/main/java/gelf/model/elements/Library.java) | Java | 439 | 51 | 74 | 564 |
| [main/java/gelf/model/elements/OutputPin.java](/main/java/gelf/model/elements/OutputPin.java) | Java | 203 | 15 | 31 | 249 |
| [main/java/gelf/model/elements/Pin.java](/main/java/gelf/model/elements/Pin.java) | Java | 55 | 4 | 20 | 79 |
| [main/java/gelf/model/elements/Stat.java](/main/java/gelf/model/elements/Stat.java) | Java | 37 | 4 | 11 | 52 |
| [main/java/gelf/model/elements/attributes/Attribute.java](/main/java/gelf/model/elements/attributes/Attribute.java) | Java | 13 | 6 | 8 | 27 |
| [main/java/gelf/model/elements/attributes/InAttribute.java](/main/java/gelf/model/elements/attributes/InAttribute.java) | Java | 76 | 12 | 22 | 110 |
| [main/java/gelf/model/elements/attributes/InputPower.java](/main/java/gelf/model/elements/attributes/InputPower.java) | Java | 29 | 12 | 8 | 49 |
| [main/java/gelf/model/elements/attributes/Leakage.java](/main/java/gelf/model/elements/attributes/Leakage.java) | Java | 83 | 13 | 22 | 118 |
| [main/java/gelf/model/elements/attributes/OutAttribute.java](/main/java/gelf/model/elements/attributes/OutAttribute.java) | Java | 105 | 13 | 27 | 145 |
| [main/java/gelf/model/elements/attributes/OutputPower.java](/main/java/gelf/model/elements/attributes/OutputPower.java) | Java | 37 | 5 | 10 | 52 |
| [main/java/gelf/model/elements/attributes/PowerGroup.java](/main/java/gelf/model/elements/attributes/PowerGroup.java) | Java | 12 | 0 | 5 | 17 |
| [main/java/gelf/model/elements/attributes/Timing.java](/main/java/gelf/model/elements/attributes/Timing.java) | Java | 54 | 5 | 14 | 73 |
| [main/java/gelf/model/elements/attributes/TimingGroup.java](/main/java/gelf/model/elements/attributes/TimingGroup.java) | Java | 13 | 0 | 5 | 18 |
| [main/java/gelf/model/elements/attributes/TimingKey.java](/main/java/gelf/model/elements/attributes/TimingKey.java) | Java | 29 | 6 | 10 | 45 |
| [main/java/gelf/model/elements/attributes/TimingSense.java](/main/java/gelf/model/elements/attributes/TimingSense.java) | Java | 12 | 0 | 5 | 17 |
| [main/java/gelf/model/elements/attributes/TimingType.java](/main/java/gelf/model/elements/attributes/TimingType.java) | Java | 15 | 0 | 5 | 20 |
| [main/java/gelf/model/exceptions/ExceedingFileSizeException.java](/main/java/gelf/model/exceptions/ExceedingFileSizeException.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/model/exceptions/InvalidArgumentException.java](/main/java/gelf/model/exceptions/InvalidArgumentException.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/model/exceptions/InvalidComparisonException.java](/main/java/gelf/model/exceptions/InvalidComparisonException.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/model/exceptions/InvalidFileFormatException.java](/main/java/gelf/model/exceptions/InvalidFileFormatException.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/model/exceptions/InvalidNameException.java](/main/java/gelf/model/exceptions/InvalidNameException.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/model/exceptions/TooManyPanelsOpenedException.java](/main/java/gelf/model/exceptions/TooManyPanelsOpenedException.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/model/exceptions/TooManySelectedException.java](/main/java/gelf/model/exceptions/TooManySelectedException.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/model/parsers/LibertyParser.java](/main/java/gelf/model/parsers/LibertyParser.java) | Java | 527 | 329 | 32 | 888 |
| [main/java/gelf/model/project/FileChooserOpen.java](/main/java/gelf/model/project/FileChooserOpen.java) | Java | 47 | 0 | 11 | 58 |
| [main/java/gelf/model/project/FileChooserSave.java](/main/java/gelf/model/project/FileChooserSave.java) | Java | 51 | 0 | 12 | 63 |
| [main/java/gelf/model/project/FileManager.java](/main/java/gelf/model/project/FileManager.java) | Java | 47 | 5 | 10 | 62 |
| [main/java/gelf/model/project/Interpolator.java](/main/java/gelf/model/project/Interpolator.java) | Java | 82 | 38 | 7 | 127 |
| [main/java/gelf/model/project/Model.java](/main/java/gelf/model/project/Model.java) | Java | 38 | 37 | 11 | 86 |
| [main/java/gelf/model/project/Project.java](/main/java/gelf/model/project/Project.java) | Java | 52 | 86 | 15 | 153 |
| [main/java/gelf/model/project/Updatable.java](/main/java/gelf/model/project/Updatable.java) | Java | 4 | 9 | 2 | 15 |
| [main/java/gelf/view/components/Button.java](/main/java/gelf/view/components/Button.java) | Java | 66 | 0 | 14 | 80 |
| [main/java/gelf/view/components/Checkbox.java](/main/java/gelf/view/components/Checkbox.java) | Java | 10 | 3 | 3 | 16 |
| [main/java/gelf/view/components/DropdownSelector.java](/main/java/gelf/view/components/DropdownSelector.java) | Java | 7 | 3 | 3 | 13 |
| [main/java/gelf/view/components/InputBox.java](/main/java/gelf/view/components/InputBox.java) | Java | 4 | 3 | 3 | 10 |
| [main/java/gelf/view/components/Label.java](/main/java/gelf/view/components/Label.java) | Java | 31 | 0 | 9 | 40 |
| [main/java/gelf/view/components/Menu.java](/main/java/gelf/view/components/Menu.java) | Java | 78 | 0 | 16 | 94 |
| [main/java/gelf/view/components/MenuBar.java](/main/java/gelf/view/components/MenuBar.java) | Java | 7 | 3 | 2 | 12 |
| [main/java/gelf/view/components/MenuItem.java](/main/java/gelf/view/components/MenuItem.java) | Java | 24 | 3 | 6 | 33 |
| [main/java/gelf/view/components/Panel.java](/main/java/gelf/view/components/Panel.java) | Java | 11 | 3 | 3 | 17 |
| [main/java/gelf/view/components/ScrollPane.java](/main/java/gelf/view/components/ScrollPane.java) | Java | 4 | 3 | 3 | 10 |
| [main/java/gelf/view/components/TextPane.java](/main/java/gelf/view/components/TextPane.java) | Java | 4 | 3 | 3 | 10 |
| [main/java/gelf/view/components/Tree.java](/main/java/gelf/view/components/Tree.java) | Java | 4 | 3 | 3 | 10 |
| [main/java/gelf/view/components/TreeMouseAdapter.java](/main/java/gelf/view/components/TreeMouseAdapter.java) | Java | 92 | 24 | 11 | 127 |
| [main/java/gelf/view/components/Window.java](/main/java/gelf/view/components/Window.java) | Java | 9 | 4 | 2 | 15 |
| [main/java/gelf/view/composites/ColorTheme.java](/main/java/gelf/view/composites/ColorTheme.java) | Java | 16 | 0 | 5 | 21 |
| [main/java/gelf/view/composites/Comparer.java](/main/java/gelf/view/composites/Comparer.java) | Java | 771 | 32 | 117 | 920 |
| [main/java/gelf/view/composites/ElementManipulator.java](/main/java/gelf/view/composites/ElementManipulator.java) | Java | 27 | 3 | 8 | 38 |
| [main/java/gelf/view/composites/HierarchyRenderer.java](/main/java/gelf/view/composites/HierarchyRenderer.java) | Java | 71 | 0 | 9 | 80 |
| [main/java/gelf/view/composites/InfoBar.java](/main/java/gelf/view/composites/InfoBar.java) | Java | 49 | 15 | 8 | 72 |
| [main/java/gelf/view/composites/InfoBarID.java](/main/java/gelf/view/composites/InfoBarID.java) | Java | 4 | 0 | 1 | 5 |
| [main/java/gelf/view/composites/MainWindow.java](/main/java/gelf/view/composites/MainWindow.java) | Java | 166 | 44 | 27 | 237 |
| [main/java/gelf/view/composites/MergeDialog.java](/main/java/gelf/view/composites/MergeDialog.java) | Java | 176 | 19 | 36 | 231 |
| [main/java/gelf/view/composites/Outliner.java](/main/java/gelf/view/composites/Outliner.java) | Java | 172 | 20 | 23 | 215 |
| [main/java/gelf/view/composites/SettingsDialog.java](/main/java/gelf/view/composites/SettingsDialog.java) | Java | 7 | 3 | 3 | 13 |
| [main/java/gelf/view/composites/SubWindow.java](/main/java/gelf/view/composites/SubWindow.java) | Java | 154 | 13 | 22 | 189 |
| [main/java/gelf/view/composites/SubWindowArea.java](/main/java/gelf/view/composites/SubWindowArea.java) | Java | 61 | 8 | 10 | 79 |
| [main/java/gelf/view/composites/TextEditor.java](/main/java/gelf/view/composites/TextEditor.java) | Java | 202 | 55 | 47 | 304 |
| [main/java/gelf/view/composites/Visualizer.java](/main/java/gelf/view/composites/Visualizer.java) | Java | 830 | 49 | 88 | 967 |
| [main/java/gelf/view/diagrams/DiagramDirector.java](/main/java/gelf/view/diagrams/DiagramDirector.java) | Java | 20 | 18 | 9 | 47 |
| [main/java/gelf/view/diagrams/DiagramWizard.java](/main/java/gelf/view/diagrams/DiagramWizard.java) | Java | 121 | 86 | 19 | 226 |
| [main/java/gelf/view/diagrams/IDiagram.java](/main/java/gelf/view/diagrams/IDiagram.java) | Java | 47 | 91 | 4 | 142 |
| [main/java/gelf/view/diagrams/IDiagramOverlayer.java](/main/java/gelf/view/diagrams/IDiagramOverlayer.java) | Java | 6 | 12 | 2 | 20 |
| [main/java/gelf/view/diagrams/IDiagramViewHelper.java](/main/java/gelf/view/diagrams/IDiagramViewHelper.java) | Java | 6 | 14 | 2 | 22 |
| [main/java/gelf/view/diagrams/IDiagramWizard.java](/main/java/gelf/view/diagrams/IDiagramWizard.java) | Java | 23 | 75 | 4 | 102 |
| [main/java/gelf/view/diagrams/SettingsProvider.java](/main/java/gelf/view/diagrams/SettingsProvider.java) | Java | 206 | 136 | 55 | 397 |
| [main/java/gelf/view/diagrams/builder/BarChartBuilder.java](/main/java/gelf/view/diagrams/builder/BarChartBuilder.java) | Java | 46 | 25 | 11 | 82 |
| [main/java/gelf/view/diagrams/builder/DiagramBuilder.java](/main/java/gelf/view/diagrams/builder/DiagramBuilder.java) | Java | 53 | 49 | 5 | 107 |
| [main/java/gelf/view/diagrams/builder/HeatMapBuilder.java](/main/java/gelf/view/diagrams/builder/HeatMapBuilder.java) | Java | 28 | 22 | 5 | 55 |
| [main/java/gelf/view/diagrams/builder/HistogramBuilder.java](/main/java/gelf/view/diagrams/builder/HistogramBuilder.java) | Java | 38 | 22 | 8 | 68 |
| [main/java/gelf/view/diagrams/builder/IBarChartBuilder.java](/main/java/gelf/view/diagrams/builder/IBarChartBuilder.java) | Java | 41 | 20 | 10 | 71 |
| [main/java/gelf/view/diagrams/builder/IDiagramBuilder.java](/main/java/gelf/view/diagrams/builder/IDiagramBuilder.java) | Java | 128 | 89 | 15 | 232 |
| [main/java/gelf/view/diagrams/builder/IHeatMapBuilder.java](/main/java/gelf/view/diagrams/builder/IHeatMapBuilder.java) | Java | 68 | 16 | 17 | 101 |
| [main/java/gelf/view/diagrams/builder/IHistogramBuilder.java](/main/java/gelf/view/diagrams/builder/IHistogramBuilder.java) | Java | 39 | 22 | 13 | 74 |
| [main/java/gelf/view/diagrams/components/BarChartBar.java](/main/java/gelf/view/diagrams/components/BarChartBar.java) | Java | 37 | 4 | 7 | 48 |
| [main/java/gelf/view/diagrams/components/BiColorScale.java](/main/java/gelf/view/diagrams/components/BiColorScale.java) | Java | 45 | 6 | 14 | 65 |
| [main/java/gelf/view/diagrams/components/DiagramAxis.java](/main/java/gelf/view/diagrams/components/DiagramAxis.java) | Java | 258 | 75 | 55 | 388 |
| [main/java/gelf/view/diagrams/components/DiagramBar.java](/main/java/gelf/view/diagrams/components/DiagramBar.java) | Java | 105 | 12 | 34 | 151 |
| [main/java/gelf/view/diagrams/components/DiagramColorScale.java](/main/java/gelf/view/diagrams/components/DiagramColorScale.java) | Java | 178 | 38 | 27 | 243 |
| [main/java/gelf/view/diagrams/components/DiagramComponent.java](/main/java/gelf/view/diagrams/components/DiagramComponent.java) | Java | 72 | 44 | 8 | 124 |
| [main/java/gelf/view/diagrams/components/DiagramComponentFactory.java](/main/java/gelf/view/diagrams/components/DiagramComponentFactory.java) | Java | 49 | 5 | 10 | 64 |
| [main/java/gelf/view/diagrams/components/DiagramLine.java](/main/java/gelf/view/diagrams/components/DiagramLine.java) | Java | 108 | 24 | 25 | 157 |
| [main/java/gelf/view/diagrams/components/DiagramValueDisplayComponent.java](/main/java/gelf/view/diagrams/components/DiagramValueDisplayComponent.java) | Java | 38 | 26 | 10 | 74 |
| [main/java/gelf/view/diagrams/components/DiagramValueLabel.java](/main/java/gelf/view/diagrams/components/DiagramValueLabel.java) | Java | 117 | 13 | 30 | 160 |
| [main/java/gelf/view/diagrams/components/HasAttachablePart.java](/main/java/gelf/view/diagrams/components/HasAttachablePart.java) | Java | 6 | 15 | 2 | 23 |
| [main/java/gelf/view/diagrams/components/HeatMapLabel.java](/main/java/gelf/view/diagrams/components/HeatMapLabel.java) | Java | 22 | 6 | 8 | 36 |
| [main/java/gelf/view/diagrams/components/HistogramBar.java](/main/java/gelf/view/diagrams/components/HistogramBar.java) | Java | 17 | 4 | 4 | 25 |
| [main/java/gelf/view/diagrams/components/HoverLabel.java](/main/java/gelf/view/diagrams/components/HoverLabel.java) | Java | 142 | 28 | 32 | 202 |
| [main/java/gelf/view/diagrams/components/Hoverable.java](/main/java/gelf/view/diagrams/components/Hoverable.java) | Java | 52 | 27 | 9 | 88 |
| [main/java/gelf/view/diagrams/components/PositionIn2DDiagram.java](/main/java/gelf/view/diagrams/components/PositionIn2DDiagram.java) | Java | 55 | 8 | 14 | 77 |
| [main/java/gelf/view/diagrams/components/PositionInFrame.java](/main/java/gelf/view/diagrams/components/PositionInFrame.java) | Java | 43 | 8 | 12 | 63 |
| [main/java/gelf/view/diagrams/components/SolidAxis.java](/main/java/gelf/view/diagrams/components/SolidAxis.java) | Java | 15 | 4 | 5 | 24 |
| [main/java/gelf/view/diagrams/components/SolidLine.java](/main/java/gelf/view/diagrams/components/SolidLine.java) | Java | 12 | 4 | 4 | 20 |
| [main/java/gelf/view/diagrams/data/DiagramData.java](/main/java/gelf/view/diagrams/data/DiagramData.java) | Java | 268 | 40 | 69 | 377 |
| [main/java/gelf/view/diagrams/data/DiagramDataExtractionStrategy.java](/main/java/gelf/view/diagrams/data/DiagramDataExtractionStrategy.java) | Java | 13 | 28 | 3 | 44 |
| [main/java/gelf/view/diagrams/data/ValueIndexExtractor.java](/main/java/gelf/view/diagrams/data/ValueIndexExtractor.java) | Java | 78 | 48 | 26 | 152 |
| [main/java/gelf/view/diagrams/indicator/DiagramViewHelper.java](/main/java/gelf/view/diagrams/indicator/DiagramViewHelper.java) | Java | 66 | 54 | 8 | 128 |
| [main/java/gelf/view/diagrams/indicator/DiagramViewHelperFactory.java](/main/java/gelf/view/diagrams/indicator/DiagramViewHelperFactory.java) | Java | 27 | 5 | 10 | 42 |
| [main/java/gelf/view/diagrams/indicator/HelperLineDisplayer.java](/main/java/gelf/view/diagrams/indicator/HelperLineDisplayer.java) | Java | 20 | 4 | 7 | 31 |
| [main/java/gelf/view/diagrams/indicator/IndicatorIdentifier.java](/main/java/gelf/view/diagrams/indicator/IndicatorIdentifier.java) | Java | 7 | 20 | 2 | 29 |
| [main/java/gelf/view/diagrams/indicator/ValueLine.java](/main/java/gelf/view/diagrams/indicator/ValueLine.java) | Java | 29 | 12 | 11 | 52 |
| [main/java/gelf/view/diagrams/indicator/ValueLineDisplayer.java](/main/java/gelf/view/diagrams/indicator/ValueLineDisplayer.java) | Java | 45 | 8 | 11 | 64 |
| [main/java/gelf/view/diagrams/indicator/ViewHelperComponent.java](/main/java/gelf/view/diagrams/indicator/ViewHelperComponent.java) | Java | 29 | 15 | 4 | 48 |
| [main/java/gelf/view/diagrams/overlayer/BarChartOverlayStrategy.java](/main/java/gelf/view/diagrams/overlayer/BarChartOverlayStrategy.java) | Java | 70 | 5 | 17 | 92 |
| [main/java/gelf/view/diagrams/overlayer/DiagramOverlayStrategy.java](/main/java/gelf/view/diagrams/overlayer/DiagramOverlayStrategy.java) | Java | 244 | 111 | 63 | 418 |
| [main/java/gelf/view/diagrams/overlayer/DiagramOverlayer.java](/main/java/gelf/view/diagrams/overlayer/DiagramOverlayer.java) | Java | 13 | 5 | 5 | 23 |
| [main/java/gelf/view/diagrams/overlayer/HistogramOverlayStrategy.java](/main/java/gelf/view/diagrams/overlayer/HistogramOverlayStrategy.java) | Java | 52 | 4 | 14 | 70 |
| [main/java/gelf/view/diagrams/type/BarChart.java](/main/java/gelf/view/diagrams/type/BarChart.java) | Java | 12 | 7 | 3 | 22 |
| [main/java/gelf/view/diagrams/type/Diagram.java](/main/java/gelf/view/diagrams/type/Diagram.java) | Java | 185 | 33 | 49 | 267 |
| [main/java/gelf/view/diagrams/type/HeatMap.java](/main/java/gelf/view/diagrams/type/HeatMap.java) | Java | 27 | 8 | 7 | 42 |
| [main/java/gelf/view/diagrams/type/Histogram.java](/main/java/gelf/view/diagrams/type/Histogram.java) | Java | 12 | 7 | 3 | 22 |
| [main/java/gelf/view/representation/CellImageGenerator.java](/main/java/gelf/view/representation/CellImageGenerator.java) | Java | 80 | 12 | 10 | 102 |
| [main/java/gelf/view/representation/CellPanel.java](/main/java/gelf/view/representation/CellPanel.java) | Java | 347 | 42 | 75 | 464 |
| [main/java/gelf/view/representation/DataPanel.java](/main/java/gelf/view/representation/DataPanel.java) | Java | 105 | 25 | 23 | 153 |
| [main/java/gelf/view/representation/ICellImageGenerator.java](/main/java/gelf/view/representation/ICellImageGenerator.java) | Java | 5 | 8 | 5 | 18 |
| [main/java/gelf/view/representation/LibraryComparePanel.java](/main/java/gelf/view/representation/LibraryComparePanel.java) | Java | 86 | 9 | 19 | 114 |
| [main/java/gelf/view/representation/LibraryPanel.java](/main/java/gelf/view/representation/LibraryPanel.java) | Java | 69 | 20 | 15 | 104 |
| [main/java/gelf/view/representation/PinComparePanel.java](/main/java/gelf/view/representation/PinComparePanel.java) | Java | 95 | 12 | 15 | 122 |
| [main/java/gelf/view/representation/PinCompareSubPanel.java](/main/java/gelf/view/representation/PinCompareSubPanel.java) | Java | 132 | 20 | 21 | 173 |

[summary](results.md)