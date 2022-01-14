wot-python-lib

> Library for working with world of tanks resources.

这个厉害了，算是好些个工具的Python版集合体：

- `PackageReader` 不需要指定目标文件具体所在的那个package文件，只需指定众多packages所在的目录便可快速提取指定目标文件
- `XmlUnpacker` 对WOT打包的xml文件解包（不太明白啥意思）
- `ModelReader` 使你可以读取wot的模型文件
- `Primitive` 包含WOT模型的数据，需要关联ModelWriter使用（这个项目里我只看到了OBJModelWriter，不知道是不是说这个）
- `RenderSet` Contains data about one render set（鬼知道这说的啥）
- `PrimitiveGroup` Contains raw group data（鬼知道这说的啥）
- `OBJModelWriter` 好像是用于把primitive文件转为obj文件