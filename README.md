# fca-clj

A command line tool for some functions of the [conexp-clj](https://github.com/tomhanika/conexp-clj) tool for Formal Concept Analysis.

## Usage

The standalone jar (in builds/uberjar) can be used via:

    $ java -jar fca-clj-0.1.0-SNAPSHOT-standalone.jar [args]

The arguments are:

```
-f, --function FUNCTION  Function to be executed.
-i, --input FILE         Input file path (for context file).
-o, --output FILE        Output file path (for output of the fca analysis).
-h, --help
```

## Options

Supported functions are:

- `draw-concept-lattice` - Draw the concept lattice of the context input using gui tool of conexp-clj. With this option, it is possible to manually edit and save the result.
- `save-concept-lattice` - Save the concept lattice of the context input. The default layout is used.
- `save-concept-lattice-dimdraw` - Save the concept lattice of the context input, using the dim-draw layout. This is only recommended for small contexts, as the computation of the dim-draw layout takes much time.

## License

Copyright © 2023 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
