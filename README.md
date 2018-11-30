This repository contains a demo project, which
* lists top Android Github repositories
* shows a simple detail view for a repository selected from the list

# Features
* Architecture: [Model-View-Intent][1] + reactive data binding (using RxJava2).
* RecyclerView loads data gradually, page by page.
* Loaded data gets cached in memory and survives aconfiguration changes (no JetPack's ViewModel is used).
* Code is written in accordance to [SOLID principles][2].
* Modularisation is done with [dependency inversion principle][3] in mind.

# Architecture

Each application screen is build around the architecture shown in the diagram below and follows the same [Model-View-Intent][1] pattern.

<img src="documentation/diagrams/architecture.png" width="600" />

*Service* is responcible for performing external operations and delivering data. It leverages Command Query Responsibility Segregation ([CQRS][4]) pattern and is capable of receiving commands and braodcasting state. *View* has an interface, which mirrors the one of the service. It can send events and accept states, which are specific to each view. *ViewModel* is a layer in between, which binds *Views* to *Services* and performs mapping between events/commands and states.

As you might notice, that *View* is also decoupled from *AndroidView*. They have different responsibilities. *AndroidView* is there to hide complexity of native Android view behind a simple contract. *View* defines proper events/state contract and adapts it to the simplified contract of *AndroidView*.

# Scoping

While *AndroidView*, *View* and *ViewModel* can be create and destroyed multiple times following the activity lifecycle, *Service* lives in a broader scope and keeps data cached in memory. In this demo services reside in application scope and views reside in fragment scope. Even better option would be to keep services in retained scope, corresponding to the scope of [ViewModel][5] in terms of Android Jetpack.

<img src="documentation/diagrams/scoping.png" width="600" />

# Packaging

The code in this app is grouped by features in the first place and then devided into modules by scopes. Thus each new implemented application screen will add at least two more modules to the app: `feature` - with UI-agnostic part of feature implementation and `feature-ui` with UI-specific implementation. Dependency goes always out of the UI-specific module.

<img src="documentation/diagrams/packaging.png" width="600" />

# Structure

This application uses [Magnet][6] dependency injection library for enabling dependency inversion. There are two packaging rules to follow:
* No modules must depend on `app` module.
* All dependencies comming from the `app` module are *assembly* dependencies. They define the features to include into the apk after the build. Removal of some dependencies will lead to removal of corresponding feature from the build.

<img src="documentation/diagrams/structure.png" width="600" />

Please notice the `debugImplementation` dependency to `leakcanary` in the `app` modules. By declaring this dependency, the app will have `leakcanary` memory leak detection active in debug build. In release build no `leakcanary` library will be included. No code modifications or no-op stubs are needed - the code is just not included in released apk 🔥.

[1]: https://cycle.js.org/model-view-intent.html
[2]: https://en.wikipedia.org/wiki/SOLID
[3]: https://en.wikipedia.org/wiki/Dependency_inversion_principle
[4]: https://martinfowler.com/bliki/CQRS.html
[5]: https://developer.android.com/jetpack/arch/viewmodel#lifecycle
[6]: https://github.com/beworker/magnet
